package com.example.basic.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.basic.R
import com.example.basic.databinding.FragmentMapBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapFragment: Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding?=null
    private val binding get()=_binding!!

    private lateinit var mMap:GoogleMap

    // PloyLine
    private val polyliOptions = PolylineOptions().width(5f).color(Color.RED)
    // 위치 정보를 얻기 위한 객체
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    // 위치 정보를 얻으면 해야할 행동이 정의된 콜백 객체
    private val locationCallback = MyLocationCallBack()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                addLocationListener()
            } else {
                Toast.makeText(requireContext(), "권한이 거부되었습니다", Toast.LENGTH_SHORT).show() } }

    /** 위치 요청 정보
     *  LocationRequest.create() Deprecated 됨 **/
    private val locationRequest by lazy {
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).apply {
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapview) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // 화면이 꺼지지 않게 하기
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        // 세로 모드로 화면 고정
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return binding.root
    }

    override fun onMapReady(map: GoogleMap) {
        mMap=map

        val marker = LatLng(35.241615, 128.695587)
        mMap.addMarker(MarkerOptions().position(marker).title("Marker LAB"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
    }

    private fun removeLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()

        checkPermission(
            cancel = {
                // 위치 정보가 필요한 이유 다이얼로그 표시
                showPermissionInfoDialog()
            },
            ok = {
                // 현재 위치를 주기적으로 요청 (권한이 필요한 부분)
                addLocationListener()
            }
        )
    }

    override fun onPause() {
        super.onPause()
        removeLocationListener()
    }

    @SuppressLint("MissingPermission")
    private fun addLocationListener() {

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    private fun checkPermission(cancel: () -> Unit, ok: () -> Unit) {
        // 위치 권한이 없는지 검사
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 허용되지 않음
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // 이전에 권한을 한 번 거부한 적인 있는 경우
                cancel()
            } else {
                // 권한 요청
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            return
        }
        ok()
    }

    private fun showPermissionInfoDialog() {
        // 다이얼로그에 권한이 필요한 이유를 설명
        AlertDialog.Builder(requireContext()).apply {
            setTitle("권한이 필요한 이유")
            setMessage("지도에 위치를 표시하려면 위치 정보 권한이 필요합니다.")
            setPositiveButton("권한 요청") { _, _ ->
                // 권한 요청
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            setNegativeButton("거부", null)
        }.show()
    }

    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            val location = locationResult.lastLocation

            location?.run {
                // 17 level로 확대하며 현재 위치로 카메라 이동
                val latLng = LatLng(latitude, longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                Log.d("MapsActivity", "위도: $latitude, 경도: $longitude")

                // PolyLine에 좌표 추가
                polyliOptions.add(latLng)

                // 선 그리기
                mMap.addPolyline(polyliOptions)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}