package com.example.tasktimerapp.view.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktimerapp.databinding.TaskRowBinding
import com.example.tasktimerapp.models.Task
import com.example.tasktimerapp.services.TimeFormat
import com.example.tasktimerapp.view.activities.MainActivity

class TaskAdapter(private val activity: MainActivity): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var tasks = listOf<Task>()
    class TaskViewHolder(val binding: TaskRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(TaskRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.binding.apply {
            tvName.text = task.name
            tvDescription.text = task.description
            tvTime.text = TimeFormat.getTimerText(task.time)

            if (task.isCompleted) {
               // cvTask.setBackgroundColor(Color.parseColor("#c5eae6"))

                checkBu.isVisible = true
            } else {
            checkBu.isVisible = false
                /// cvTask.setBackgroundColor(Color.parseColor("#8CD5CD"))
            }

            tvTime.setOnClickListener {
                activity.startStopTapped(tvTime, task)
            }
//            checkBu.setOnClickListener {
//                activity.updateIsCompletedTask(task.isCompleted, task)
//
//            }

            cvTask.setOnClickListener {
                activity.updateIsCompletedTask(task.isCompleted, task)
            }
            btnDelete.setOnClickListener {
                activity.deleteTask(task)
            }
        }
    }

    override fun getItemCount() = tasks.size

    fun updateRV(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

}