package com.example.basic.todoList.data

import kotlinx.coroutines.flow.Flow

// 앱에서 사용하는 데이터와 그 데이터 통신을 하는 역할
class TodoRepository(private val todoDao: TodoDao) {
    val readAllData : Flow<List<Todo>> = todoDao.readAllData()

    suspend fun addTodo(todo: Todo){
        todoDao.addTodo(todo)
    }

    suspend fun updateTodo(todo: Todo){
        todoDao.updateTodo(todo)
    }

    suspend fun deleteTodo(todo: Todo){
        todoDao.deleteTodo(todo)
    }

    fun readDateData(year : Int, month : Int, day : Int): Flow<List<Todo>> {
        return todoDao.readDateData(year,month,day)
    }
}