package com.example.codeclause_todo_app

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTodo(todo: Todo)

    @Query("DELETE FROM Todo WHERE  title = :title AND body = :body")
    suspend fun deleteTodo(title: String,body: String)

    @Query("SELECT * FROM Todo")
    suspend fun showAllTodos(): MutableList<Todo>
}
