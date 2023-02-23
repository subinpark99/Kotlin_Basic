package com.example.basic.todoList.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.basic.todoList.ui.CalendarFragment
import com.example.basic.todoList.ui.TodoListFragment

@RequiresApi(Build.VERSION_CODES.O)
class ViewPagerAdapter (fragment : Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TodoListFragment()
            else -> CalendarFragment()
        }
    }
}