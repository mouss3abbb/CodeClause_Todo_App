package com.example.codeclause_todo_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codeclause_todo_app.databinding.ActivityNewTodoBinding

class NewTodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewTodoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}