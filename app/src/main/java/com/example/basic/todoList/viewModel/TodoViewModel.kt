package com.example.basic.todoList.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.basic.todoList.data.Todo
import com.example.basic.todoList.data.TodoDatabase
import com.example.basic.todoList.data.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// 뷰모델은 DB에 직접 접근하지 않아야함. Repository 에서 데이터 통신.

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData : LiveData<List<Todo>>
    private val repository : TodoRepository

    init{
        val memoDao = TodoDatabase.getDatabase(application)!!.todoDao()
        repository = TodoRepository(memoDao)
        readAllData = repository.readAllData.asLiveData()
    }

    fun addTodo(todo : Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTodo(todo)
        }
    }

    fun updateTodo(todo : Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo : Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
        }
    }

    fun readDateData(year : Int, month : Int, day : Int): LiveData<List<Todo>> {
        return repository.readDateData(year,month,day).asLiveData()
    }
}