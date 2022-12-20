package com.example.codeclause_todo_app

import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.marginTop
import com.example.codeclause_todo_app.databinding.ActivityNewTodoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_new_todo.*

@AndroidEntryPoint
class NewTodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewTodoBinding
    private val viewModel: NewTodoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        viewModel.titleEt = binding.titleEt
        viewModel.bodyEt = binding.bodyEt

    }
}