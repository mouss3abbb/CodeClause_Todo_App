package com.example.codeclause_todo_app

import android.graphics.Canvas
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.codeclause_todo_app.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_main.*
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
                Snackbar.make(todoRv,"Todo deleted",Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        if (todo != null) {
                            runBlocking{
                                db.addTodo(todo)
                                viewModel.todoListLiveData.value = db.showAllTodos().reversed() as MutableList<Todo>
                            }
                            binding.todoRv.adapter?.notifyDataSetChanged()
                        }
                    }.show()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                    .addBackgroundColor(resources.getColor(R.color.color1))
                    .addActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }).attachToRecyclerView(binding.todoRv)

    }
}