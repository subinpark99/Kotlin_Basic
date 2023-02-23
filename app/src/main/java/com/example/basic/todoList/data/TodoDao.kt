package com.example.basic.todoList.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    // OnConflictStrategy.IGNORE = 동일한 아이디가 있을 시 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTodo(todo : Todo)

    @Update
    suspend fun updateTodo(todo : Todo)

    @Delete
    suspend fun deleteTodo(todo : Todo)

    @Query("SELECT * FROM Todo ORDER BY year DESC, month DESC, day DESC, id DESC")
    fun readAllData() : Flow<List<Todo>>

    // 날짜 정보를 입력받아 그 날짜에 해당하는 메모만 반환
    @Query("SELECT * FROM Todo WHERE year = :year AND month = :month AND day = :day ORDER BY id DESC")
    fun readDateData(year : Int, month : Int, day : Int) : Flow<List<Todo>>
}