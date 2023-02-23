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
import com.example.basic.databinding.FragmentTodoListBinding
import com.example.basic.todoList.adapter.TodoListAdapter
import com.example.basic.todoList.data.Todo
import com.example.basic.todoList.viewModel.TodoViewModel
import com.example.kotlin.bottom.todo.DialogInterface
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class TodoListFragment : Fragment(), DialogInterface {
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    private val adapter: TodoListAdapter by lazy { TodoListAdapter(todoViewModel) }
    private val todoViewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTodoListBinding.inflate(inflater, container, false)

        recyclerView()

        binding.floatingButton.setOnClickListener {
            onFabClicked()
        }
        return binding.root
    }

    private fun recyclerView(){
        binding.todoListRv.layoutManager=LinearLayoutManager(
            requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.todoListRv.adapter=adapter

        // 리스트 관찰, 변경시 어댑터에 전달
        todoViewModel.readAllData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun onFabClicked() {
        val myCustomDialog = AddTodoDialog(requireContext(),this)
        myCustomDialog.show()
    }


    override fun onAcceptClicked(content: String) {

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        val day = cal.get(Calendar.DATE)

        val todo= Todo(0,content,year,month,day)
        todoViewModel.addTodo(todo)

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}