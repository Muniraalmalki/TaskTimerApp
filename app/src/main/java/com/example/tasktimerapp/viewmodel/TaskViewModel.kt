package com.example.tasktimerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.tasktimerapp.models.Task
import com.example.tasktimerapp.models.room.TaskDatabase
import com.example.tasktimerapp.models.room.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository
    private val tasks: LiveData<List<Task>>
    private val tasksIscomplete: LiveData<List<Task>>
    private val tasksInprogrees: LiveData<List<Task>>
    init {
        val taskDao = TaskDatabase.getDatabase(application).TaskDao()
        repository = TaskRepository(taskDao)
        tasks = repository.getTasks
        tasksIscomplete = repository.getTasksIscomplete
        tasksInprogrees = repository.getTasksInprogrees
    }

    fun getTask(): LiveData<List<Task>> {
        return tasks
    }
    fun getTaskInprogrees(): LiveData<List<Task>> {
        return tasksInprogrees
    }

    fun getTaskIscomplete(): LiveData<List<Task>> {


        return tasksIscomplete
    }

    fun addTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addTask(task)
        }
    }

    fun editTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateTask(task)
        }


    }

    fun deleteTask(task: Task){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteTask(task)
        }
    }



}