package com.example.basic.todoList.ui

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.basic.databinding.FragmentTodoBinding
import com.example.basic.todoList.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTodoBinding.inflate(inflater, container, false)

        viewPager()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun viewPager(){

        // 뷰페이저에 어댑터 연결
        binding.pager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.pager) {tab, position ->
            when(position) {
                0 -> tab.text = "todolist"
                1 -> tab.text = "calendar"
            }
        }.attach()
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}