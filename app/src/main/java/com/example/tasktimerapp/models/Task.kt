package com.example.tasktimerapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val description: String,
    val category: String,
    var time: Double,
    val data: String,
    var isCompleted: Boolean
)
