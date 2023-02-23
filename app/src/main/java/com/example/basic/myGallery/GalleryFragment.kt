package com.example.basic.myGallery

import android.app.AlertDialog
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.basic.databinding.FragmentGalleryBinding
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding?=null
    private val binding get()=_binding!!

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                //  권한이 허락됨
                getAllPhotos()
            } else {
                Toast.makeText(requireContext(), "권한이 거부되었습니다", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)

        // 권한이 부여되었는지 확인
        if ( ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE

            ) != PackageManager.PERMISSION_GRANTED
        ) {  // 이전에 권한이 허용되지 않음
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {  // 이전에 이미 권한이 거부되었을 때 설명
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("권한이 필요한 이유")
                    setMessage("사진 정보를 얻으려면 외부 저장소 권한이 필요합니다.")
                    setPositiveButton("권한 요청") { _, _ ->
                        // 권한 요청
                        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE) }
                    setNegativeButton("거부", null)
                }.show()
            } else {
                // 권한 요청
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
        // 권한이 이미 허용됨
        getAllPhotos()

        return binding.root
    }

    private fun getAllPhotos(){
        val uris= mutableListOf<Uri>()

        requireActivity().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,null,null,
            "${MediaStore.Images.ImageColumns.DATE_TAKEN} DESC" // 찍은 날짜 내림차순
        )?.use { cursor->  // use() 확장 함수 사용 시, 사용 끝나고 자동으로 close() 호출
            while (cursor.moveToNext()){
                val id=cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)) // 사진 정보 id
                val contentUri=ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id
                )  // uri 얻기

                uris.add(contentUri)
            }
        }

        Log.d("galleryFragment","getAllPhotos: $uris")

        // viewpager2 어댑터 연결
        val adapter=PagerAdapter(requireActivity().supportFragmentManager,lifecycle)
        adapter.uris=uris

        binding.viewPager.adapter=adapter

        timer(period = 3000){ // 3초 마다 실행
            activity?.runOnUiThread{
                if(binding.viewPager.currentItem < adapter.itemCount -1){  // 현재 페이지가 마지막 페이지가 아니면
                    binding.viewPager.currentItem = binding.viewPager.currentItem +1  // 다음 페이지로 변경
                }else{
                    binding.viewPager.currentItem=0  // 마지막 페이지이면 첫 페이지로 변경
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}