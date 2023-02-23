package com.example.basic.myGallery

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.basic.databinding.FragmentAddPhotoBinding


private const val ARG_URI="uri"  // 파일 내 어디서든 사용 가능

@Suppress("DEPRECATION")
class AddPhotoFragment : Fragment() {

    private var _binding: FragmentAddPhotoBinding?=null
    private val binding get()=_binding!!

    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getParcelable<Uri>(ARG_URI)?.let {
            uri = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

      @SuppressLint("Recycle")
      override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
          super.onViewCreated(view, savedInstanceState)

          val imageView=binding.imageView
          val descriptor=requireContext().contentResolver.openFileDescriptor(uri,"r")
          descriptor?.use {
              val bitmap= BitmapFactory.decodeFileDescriptor(descriptor.fileDescriptor)
              imageView.load(bitmap)
          }
      }

      companion object{
          @JvmStatic
          fun newInstance(uri:Uri)=
              AddPhotoFragment().apply {
                  arguments= Bundle().apply {
                      putParcelable(ARG_URI,uri)
                  }
              }
      }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}