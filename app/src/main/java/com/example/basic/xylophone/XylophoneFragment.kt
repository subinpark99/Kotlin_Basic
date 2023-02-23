package com.example.basic.xylophone

import android.content.pm.ActivityInfo
import android.media.SoundPool
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.basic.R
import com.example.basic.databinding.FragmentXylophoneBinding

class XylophoneFragment: Fragment() {

    private var _binding: FragmentXylophoneBinding?=null
    private val binding get()=_binding!!

    private val soundPool=SoundPool.Builder().setMaxStreams(8).build()

    private val sounds by lazy {
        listOf(
            Pair(binding.do1, R.raw.do1),
            Pair(binding.re, R.raw.re),
            Pair(binding.mi, R.raw.mi),
            Pair(binding.fa, R.raw.fa),
            Pair(binding.sol, R.raw.sol),
            Pair(binding.la, R.raw.la),
            Pair(binding.si, R.raw.si),
            Pair(binding.do2, R.raw.do2),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentXylophoneBinding.inflate(inflater, container, false)

        requireActivity().requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        sounds.forEach { tune(it) }  // sounds 리스트의 요소를 하나씩 꺼내서 tune()에 전달

        return binding.root
    }

    private fun tune(pitch: Pair<TextView, Int>) {
        val soundId = soundPool.load(requireContext(), pitch.second, 1)
        pitch.first.setOnClickListener {
            soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f)
        }
    }
    override fun onPause() {
        super.onPause()
        requireActivity().requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding=null
        soundPool.release()
    }
}