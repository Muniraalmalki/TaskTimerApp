package com.example.tasktimerapp.models.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tasktimerapp.models.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM task where isCompleted = 0 and time = 0.0")
    fun getTasks() : LiveData<List<Task>>

    @Query("SELECT * FROM task where isCompleted = 1")
    fun getTasksIscomplete() : LiveData<List<Task>>

    @Query("SELECT * FROM task where isCompleted = 0 and time > 0.0")
    fun getTasksInprogrees() : LiveData<List<Task>>

    @Update
    suspend fun updateTask(task: Task)



    @Delete
    suspend fun deleteTask(task: Task)
}