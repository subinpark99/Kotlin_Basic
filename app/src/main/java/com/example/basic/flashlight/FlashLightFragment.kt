package com.example.basic.flashlight

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.basic.databinding.FragmentFlashlightBinding

class FlashLightFragment: Fragment() {

    private var _binding: FragmentFlashlightBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlashlightBinding.inflate(inflater, container, false)


        binding.flashSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                requireActivity().startService(Intent(requireContext(),TorchService::class.java).apply{
                    action ="on"
                })
            }else{
                requireActivity().startService(Intent(requireContext(),TorchService::class.java).apply{
                    action ="off"
                })
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}