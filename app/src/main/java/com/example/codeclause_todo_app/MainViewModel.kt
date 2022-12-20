package com.example.codeclause_todo_app

import android.app.Application
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val app: Application, val db: TodoDao): ViewModel() {

    val todoListLiveData = MutableLiveData<MutableList<Todo>>()

    init {
        runBlocking {
            todoListLiveData.value = db.showAllTodos()
            todoListLiveData.value!!.reverse()
        }
    }
    fun addNewTodo(){
        app.startActivity(
            Intent(app,NewTodoActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}