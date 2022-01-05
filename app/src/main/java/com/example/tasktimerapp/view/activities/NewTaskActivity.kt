package com.example.tasktimerapp.view.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.Calendar.getInstance
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.tasktimerapp.databinding.ActivityNewTaskBinding
import com.example.tasktimerapp.models.Task
import com.example.tasktimerapp.viewmodel.TaskViewModel
import java.util.*

class NewTaskActivity : AppCompatActivity() {

    private val taskViewModel by lazy { ViewModelProvider(this).get(TaskViewModel::class.java) }

    private lateinit var binding: ActivityNewTaskBinding
    private lateinit var dateButton: Button
    private lateinit var spinner: Spinner

    private var categorySelected = ""
    private var dateSelected = ""


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()
        setupDataPicker()
        setupButton()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setupDataPicker() {
        val myCanceler = getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCanceler.set(Calendar.YEAR, year)
            myCanceler.set(Calendar.MONTH, month)
            myCanceler.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCanceler)
        }
        dateButton = binding.dateBu
        dateButton.setOnClickListener {
            DatePickerDialog(this, datePicker, myCanceler.get(Calendar.YEAR),
                myCanceler.get(Calendar.MONTH), myCanceler.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateLabel(myCanceler: Calendar?) {
        val myFormat = "dd.MM.yyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        dateButton.text = sdf.format(myCanceler?.time)
        dateSelected = sdf.format(myCanceler?.time)
    }

    private fun setupSpinner() {
        spinner = binding.spinner
        val category = arrayListOf("Daily tasks", "monthly tasks", "Assignment")
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, category)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                categorySelected = category[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun setupButton() {
        binding.apply {
            btnCreateTask.setOnClickListener {
                val name = etName.text.toString()
                val description = etDescription.text.toString()
                if (name.isNotEmpty() && description.isNotEmpty() && dateSelected.isNotEmpty() && categorySelected.isNotEmpty()) {
                    etName.text.clear()
                    etDescription.text.clear()
                    val newTask = Task(0, name, description, categorySelected, 0.0, dateSelected, false)
                    taskViewModel.addTask(newTask)
                    val intent = Intent(this@NewTaskActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@NewTaskActivity, "empty!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}
