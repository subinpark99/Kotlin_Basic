package com.example.basic.bmiCalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.basic.R
import com.example.basic.databinding.FragmentBmiResultBinding
import kotlin.math.pow

class BmiResultFragment : Fragment() {

    private var _binding: FragmentBmiResultBinding?=null
    private val binding get()=_binding!!

    private val args: BmiResultFragmentArgs by navArgs()  // 데이터 꺼내 오기

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBmiResultBinding.inflate(inflater, container, false)

        val height=args.height
        val weight=args.weight

        val bmi=  weight.toDouble() / (height.toDouble() / 100.0).pow(2.0)
        Toast.makeText(requireContext(),"$bmi",Toast.LENGTH_SHORT).show()

        when{  // 결과 표시
            bmi>=35 -> binding.bmiRstTv.text="고도 비만"
            bmi>=30 -> binding.bmiRstTv.text="2단계 비만"
            bmi>=25 -> binding.bmiRstTv.text="1단계 비만"
            bmi>=23 -> binding.bmiRstTv.text="과체중"
            bmi>=18.5 -> binding.bmiRstTv.text="정상"
             else -> binding.bmiRstTv.text="저체중"
        }

        when{  // 이미지 표시
            bmi>=23 -> binding.bmiRstIv.setImageResource(R.drawable.icon_sad)
            bmi>=18.5-> binding.bmiRstIv.setImageResource(R.drawable.icon_smile)
            else-> binding.bmiRstIv.setImageResource(R.drawable.icon_bad)
        }
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}