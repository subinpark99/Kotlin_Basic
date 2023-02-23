package com.example.basic.todoList.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basic.databinding.FragmentTodoCalendarBinding
import com.example.basic.todoList.adapter.TodoListAdapter
import com.example.basic.todoList.data.Todo
import com.example.basic.todoList.viewModel.TodoViewModel
import com.example.kotlin.bottom.todo.DialogInterface


class CalendarFragment : Fragment(),DialogInterface {
    private var _binding: FragmentTodoCalendarBinding? = null
    private val binding get() = _binding!!

    private val todoViewModel: TodoViewModel by viewModels() // 뷰모델 연결
    private val adapter: TodoListAdapter by lazy { TodoListAdapter(todoViewModel) }

    private var year : Int = 0
    private var month : Int = 0
    private var day : Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTodoCalendarBinding.inflate(inflater, container, false)

        calendarView()
        binding.checkFloatingBtn.setOnClickListener {
            onFabClicked()
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calendarView(){

        binding.calendarRv.layoutManager= LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL,false)
        binding.calendarRv.adapter=adapter

        binding.calendarViewCv.setOnDateChangeListener { _, year, month, dayOfMonth ->

            this.year = year
            this.month = month+1
            this.day = dayOfMonth

            todoViewModel.readDateData(this.year,this.month,this.day).observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onFabClicked() {
        val myCustomDialog = AddTodoDialog(requireContext(),this)
        myCustomDialog.show()
    }


    override fun onAcceptClicked(content: String) {
        val todo= Todo(0,content,year,month,day)
        todoViewModel.addTodo(todo)
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}