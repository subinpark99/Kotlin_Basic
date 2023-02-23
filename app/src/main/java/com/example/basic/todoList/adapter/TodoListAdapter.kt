package com.example.basic.todoList.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.basic.databinding.ItemTodoListBinding
import com.example.basic.todoList.data.Todo
import com.example.basic.todoList.ui.UpdateTodoDialog
import com.example.basic.todoList.viewModel.TodoViewModel
import com.example.kotlin.bottom.todo.UpdateDialogInterface


class TodoListAdapter(private val viewModel: TodoViewModel): ListAdapter<Todo, TodoListAdapter.TodoViewHolder>(
    diffUtil
) {

    private lateinit var binding: ItemTodoListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        binding=ItemTodoListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TodoViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position),viewModel)
    }

    class TodoViewHolder(private val binding:ItemTodoListBinding):
        RecyclerView.ViewHolder(binding.root),UpdateDialogInterface{

        private lateinit var todos:Todo
        lateinit var viewModel: TodoViewModel

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(currentTodo:Todo,viewModel: TodoViewModel){
            binding.todo=currentTodo
            this.viewModel=viewModel

            binding.itemTodoDeleteTv.setOnClickListener {
                viewModel.deleteTodo(currentTodo)
            }

            binding.itemTodoEditTv.setOnClickListener {
                todos = currentTodo
                val dialog= UpdateTodoDialog(binding.itemTodoEditTv.context,this)
                dialog.show()
            }

        }

        override fun onAcceptClicked(content: String) {
            val update=Todo(todos.id,content,todos.year,todos.month,todos.day)
            viewModel.updateTodo(update)
        }
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<Todo>(){
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo) = oldItem == newItem
            override fun areContentsTheSame(oldItem: Todo, newItem: Todo) = oldItem == newItem
        }
    }
}