package com.example.codeclause_todo_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.codeclause_todo_app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    @Inject
    lateinit var db: TodoDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        viewModel.todoListLiveData.observe(this){
            binding.todoRv.adapter = TodoAdapter(it)
        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val todo = viewModel.todoListLiveData.value?.get(viewHolder.adapterPosition)
                viewModel.todoListLiveData.value?.removeAt(viewHolder.adapterPosition)
                binding.todoRv.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                runBlocking {
                    if (todo != null) {
                        db.deleteTodo(todo.title, todo.body)
                    }
                }
                Toast.makeText(applicationContext,"Deleted todo",Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(binding.todoRv)

    }
}