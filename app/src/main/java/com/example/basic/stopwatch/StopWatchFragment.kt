package com.example.basic.stopwatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.basic.R
import com.example.basic.databinding.FragmentStopWatchBinding
import java.util.*
import kotlin.concurrent.timer

class StopWatchFragment : Fragment() {

    private var _binding: FragmentStopWatchBinding?=null
    private val binding get()=_binding!!

    private var time=0
    private var timerTask: Timer?=null
    private var isRunning= false
    private var lap=1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStopWatchBinding.inflate(inflater, container, false)

        binding.playBtn.setOnClickListener {
            isRunning=!isRunning  // 변수 값 반전

            if(isRunning){
                start()
            }else{
                pause()
            }
        }

        binding.lapBtn.setOnClickListener {
            recordLapTime()
        }

        binding.refreshBtn.setOnClickListener {
            reset()
        }

        return binding.root
    }

    private fun start(){

        binding.playBtn.setImageResource(R.drawable.pause)
        timerTask= timer(period = 10){
            time++
            val sec=time/100
            val milli= time%100

            activity?.runOnUiThread{
                binding.secTv.text="$sec"
                binding.milliTv.text="$milli"
            }
        }
    }

    private fun pause(){

        binding.playBtn.setImageResource(R.drawable.icon_play_arrow)
        timerTask?.cancel()  // 실행 중인 타이머가 있으면 취소
    }

    @SuppressLint("SetTextI18n")
    private fun reset(){

        timerTask?.cancel()

        time=0
        isRunning=false
        binding.playBtn.setImageResource(R.drawable.icon_play_arrow)
        binding.secTv.text="0"
        binding.milliTv.text="00"

        binding.layLayout.removeAllViews()  // 모든 랩 타임 제거
        lap=1
    }

    @SuppressLint("SetTextI18n")
    private fun recordLapTime(){

        val lapTime=this.time   // 현재 시간 저장
        val tv= TextView(requireContext())  // 텍스트 동적 생성
        tv.text="$lap LAP : ${lapTime / 100}.${lapTime%100}"  // 시간 계산

        binding.layLayout.addView(tv,0)  // 텍스트 뷰를 레이아웃의 맨 위에 추가
        lap++
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}