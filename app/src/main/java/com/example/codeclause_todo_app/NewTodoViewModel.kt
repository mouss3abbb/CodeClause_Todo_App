package com.example.codeclause_todo_app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.media.Image
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.activity_new_todo.view.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class NewTodoViewModel @Inject constructor(val app: Application, private val db: TodoDao): ViewModel() {

    @SuppressLint("StaticFieldLeak")
    lateinit var titleEt: EditText
    @SuppressLint("StaticFieldLeak")
    lateinit var bodyEt: EditText


    fun done(){
        if(titleEt.text.toString().isEmpty()){
            Toast.makeText(app,"Please provide title",Toast.LENGTH_SHORT).show()
            return
        }
        Toast.makeText(app,"Added new todo",Toast.LENGTH_SHORT).show()
        runBlocking {
            db.addTodo(
                Todo(
                    titleEt.text.toString(),
                    bodyEt.text.toString()
                )
            )
        }

        app.startActivity(
            Intent(app.applicationContext,MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }
}