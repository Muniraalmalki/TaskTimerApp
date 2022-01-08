package com.example.tasktimerapp.models.room

import androidx.lifecycle.LiveData
import com.example.tasktimerapp.models.Task
import com.example.tasktimerapp.models.room.TaskDao

class TaskRepository(private val taskDao: TaskDao) {

    val getTasks: LiveData<List<Task>> = taskDao.getTasks()

    val getTasksIscomplete: LiveData<List<Task>> = taskDao.getTasksIscomplete()
    val getTasksInprogrees: LiveData<List<Task>> = taskDao.getTasksInprogrees()

    suspend fun addTask(task: Task){
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }

}