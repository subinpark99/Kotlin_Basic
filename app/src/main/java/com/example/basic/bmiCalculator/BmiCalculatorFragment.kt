package com.example.basic.bmiCalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.basic.databinding.FragmentBmiCalculatorBinding

class BmiCalculatorFragment:Fragment() {

    private var _binding: FragmentBmiCalculatorBinding? = null
    private val binding get() = _binding!!

    private lateinit var height: String
    private lateinit var weight: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBmiCalculatorBinding.inflate(inflater, container, false)

        binding.submitBtn.setOnClickListener {
            move()
        }

        loadData()  // 실행 시 마지막에 저장한 값 불러오기

        return binding.root
    }

    private fun move() {
        height = binding.heightEt.text.toString()
        weight = binding.weightEt.text.toString()
        val action =BmiCalculatorFragmentDirections.actionBmiCalculatorFragmentToBmiResultFragment(
            height,
            weight
        )

        if (height.isNotBlank() && weight.isNotBlank()) {
            findNavController().navigate(action)
        }

        saveData()
    }

    @SuppressLint("CommitPrefEdits")
    private fun saveData() {  // sharedPreference 로 객체 저장
        val pref = activity?.getSharedPreferences("pref", 0)
        val edit = pref?.edit()

        edit?.putString("height", height)
        edit?.putString("weight", weight)
            ?.apply()
    }

    private fun loadData() {
        val pref = activity?.getSharedPreferences("pref", 0)
        val heightData = pref?.getString("height", "")
        val weightData = pref?.getString("weight", "")

        if (heightData != null && weightData != null) {
            binding.heightEt.setText(heightData.toString())
            binding.weightEt.setText(weightData.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}