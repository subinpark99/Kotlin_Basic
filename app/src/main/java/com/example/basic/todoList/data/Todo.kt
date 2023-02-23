package com.example.basic.todoList.data


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var id : Int ,
    val content: String,
    val year : Int,
    val month : Int,
    val day : Int
)