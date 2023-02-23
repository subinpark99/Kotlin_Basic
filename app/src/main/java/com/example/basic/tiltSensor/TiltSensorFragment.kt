package com.example.basic.tiltSensor

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment


class TiltSensorFragment: Fragment(),SensorEventListener {

    private val sensorManager by lazy {
        requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager }

    private lateinit var tiltView: TiltView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        requireActivity().requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE  // 화면 가로 모드 고정
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)  // 화면이 꺼지지 않게

        tiltView= TiltView(requireContext())

        return tiltView
    }

    override fun onResume() {  // 실행 시에만 센서 동작
        super.onResume()

        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),  // 가속도 센서 지정
            SensorManager.SENSOR_DELAY_NORMAL)  // 센서값을 얼마나 자주 받을 지(보통)
    }


    override fun onPause() {   // 화면이 꺼지기 직전에 센서 해제
        super.onPause()
        sensorManager.unregisterListener(this)
        requireActivity().requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
    }

    override fun onSensorChanged(event: SensorEvent?) {
        /** values[0] - x축 값, 위로 기울이면 -10~0, 아래로 기울이면 0~10
         *  values[1] -y축 값, 왼쪽으로 기울이면 -10~0, 오른쪽으로 기울이면 0~10
         *  values[2] -z축 값, 미사용 **/

        event?.let {

            tiltView.onSensorEvent(event)

            Log.d("TiltFragment","onSensorChanged: x :" +
                    "${event.values[0]}, y: ${event.values[1]}, z: ${event.values[2]}"
            )
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}