package com.example.tasktimerapp.view.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.R
import com.example.tasktimerapp.databinding.ActivityMainBinding
import com.example.tasktimerapp.models.Task
import com.example.tasktimerapp.services.TimeFormat
import com.example.tasktimerapp.view.adapters.TaskAdapter
import com.example.tasktimerapp.viewmodel.TaskViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private val taskViewModel by lazy { ViewModelProvider(this).get(TaskViewModel::class.java) }
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var binding: ActivityMainBinding
    var userInput = ""
    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    var time = 0.0
    private var taskSelected: Task? = null

    private var timerStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRV()



        taskViewModel.getTask().observe(this, {

                tasks ->
            adapter.updateRV(tasks)
            binding.toDoDisTV.text = tasks.size.toString()
        })


        taskViewModel.getTaskInprogrees().observe(this, { tasks ->
            binding.inProgressDisTV.text = tasks.size.toString()
        })

        taskViewModel.getTaskIscomplete().observe(this, { tasks ->
            binding.doneDisTV.text = tasks.size.toString()

        })

        binding.toDoImageView.setOnClickListener {

            taskViewModel.getTask().observe(this, {

                    tasks ->
                adapter.updateRV(tasks)
            })
        }


        binding.doneImageV.setOnClickListener {

            taskViewModel.getTaskIscomplete().observe(this, { tasks ->
                adapter.updateRV(tasks)
                binding.doneDisTV.text = tasks.size.toString()

            })
        }

        binding.inProgressImageV.setOnClickListener {

            taskViewModel.getTaskInprogrees().observe(this, { tasks ->
                adapter.updateRV(tasks)
                binding.inProgressDisTV.text = tasks.size.toString()
            })
        }



        binding.ivAddNewTask.setOnClickListener {
            val intent = Intent(this, NewTaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRV() {
        taskRecyclerView = binding.rvTask
        adapter = TaskAdapter(this)
        taskRecyclerView.adapter = adapter
        taskRecyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun updateTaskTime() {
        taskSelected?.time = time
        taskViewModel.editTask(taskSelected!!)
    }

    fun updateIsCompletedTask(isCompleted: Boolean, task: Task) {
        task.isCompleted = !isCompleted
        taskViewModel.editTask(task)
    }

    private fun updateTask(task: Task) {

     taskViewModel.editTask(task)
    }

    private fun deleteTask(task: Task) {
        taskViewModel.deleteTask(task)
    }


    fun startStopTapped(tvTime: TextView, task: Task) {
        timer = Timer()
        if (!timerStarted) {
            taskSelected = task
            time = task.time
            timerStarted = true
            startTimer(tvTime)
        } else {
            stopTapped()
        }
    }

    private fun stopTapped() {
        timerStarted = false
        Log.d("Main", "When stop: $time") // *
        // should save
        updateTaskTime()
        timerTask!!.cancel()
    }

    private fun startTimer(tvTime: TextView) {
        timerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    time++
                    tvTime.text = TimeFormat.getTimerText(time)
                }
            }
        }
        timer!!.scheduleAtFixedRate(timerTask, 0, 1000)
    }

    fun dialog(updateOrDelete: String, task: Task) {


        if (updateOrDelete.equals("update")) {
            val builder = AlertDialog.Builder(this)
            //  set title for alert dialog
            builder.setTitle("Update Task")

            var input = EditText(this)
            input.setText(task.name)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)


            //performing positive action
            builder.setPositiveButton("update") { dialogInterface, which ->

                userInput = input.text.toString()
                val updateTask = Task(task.id,userInput,task.description,task.category,task.time,task.data,task.isCompleted)
                updateTask(updateTask)


            }
            builder.setNegativeButton("CANCEL") { dialogInterface, which -> }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            // alertDialog.setCancelable(false)
            alertDialog.show()
        } else {
            val builder = AlertDialog.Builder(this)
            //  set title for alert dialog
            builder.setTitle("Are you sure to delete Task  ")
            var input = EditText(this)
            input.setText(task.name)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)
            //performing positive action
            builder.setPositiveButton("Delete") { dialogInterface, which ->
                userInput = input.text.toString()
                deleteTask(task)

            }
            builder.setNegativeButton("CANCEL") { dialogInterface, which -> }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(true)
            alertDialog.show()
        }


    }

}