package com.example.tasktimerapp.models.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tasktimerapp.models.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM task ORDER BY id ASC")
    fun getTasks() : LiveData<List<Task>>

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}