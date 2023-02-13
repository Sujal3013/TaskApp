package com.example.taskdone

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TimePicker
import androidx.room.Room
import java.util.*
import kotlinx.android.synthetic.main.activity_task.*
import com.example.taskdone.Database.todoDatabase
import com.example.taskdone.modal.TodoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat


const val DB_NAME = "todo.db"

class TaskActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var myCalender: Calendar

    lateinit var datesetListener: DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: OnTimeSetListener

    var finalDate = 0L
    var finaltime = 0L

    private val labels = arrayListOf("Personal", "Business", "Studies", "Household")

    val db by lazy {
        todoDatabase.getDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        Dateedt.setOnClickListener(this)
        timeedt.setOnClickListener(this)
        save.setOnClickListener(this)

        setUpSpinner()
    }

    private fun setUpSpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, labels)
        labels.sort()
        spinner.adapter = adapter

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.Dateedt -> {
                setListener()
            }
            R.id.timeedt -> {
                setTimeListener()
            }
            R.id.save -> {
                saveTodo()
            }
        }
    }

    private fun saveTodo() {
        val category = spinner.selectedItem.toString()
        val title = titleInplay.editText?.text.toString()
        val description = taskInplay.editText?.text.toString()

        GlobalScope.launch(Dispatchers.Main) {
            val id = withContext(Dispatchers.IO) {
                return@withContext db.todoDao().insertTask(
                    TodoModel(
                        title,
                        description,
                        category,
                        finalDate,
                        finaltime
                    )
                )
            }
            finish()
        }
    }

    private fun setTimeListener() {
        myCalender = Calendar.getInstance()
        timeSetListener = OnTimeSetListener { _: TimePicker, hourofDay: Int, minute: Int ->
            myCalender.set(Calendar.HOUR_OF_DAY, hourofDay)
            myCalender.set(Calendar.MINUTE, minute)
            updateTime()

        }

        val timePickerDialog = TimePickerDialog(
            this, timeSetListener, myCalender.get(Calendar.HOUR_OF_DAY),
            myCalender.get(Calendar.MINUTE), false
        )
        timePickerDialog.show()
    }

    private fun updateTime() {
        val myformat = "h:mm a"
        val sdf = SimpleDateFormat(myformat)
        finaltime = myCalender.time.time
        Dateedt.setText(sdf.format(myCalender.time))
    }

    private fun setListener() {
        myCalender = Calendar.getInstance()

        datesetListener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                myCalender.set(Calendar.YEAR, year)
                myCalender.set(Calendar.MONTH, month)
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDate()

            }

        val datePickerDialog = DatePickerDialog(
            this, datesetListener, myCalender.get(Calendar.YEAR),
            myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun updateDate() {
        //TUE ,8 JAN 2023
        val myformat = "EEE, d MMM yyyy"
        val sdf = SimpleDateFormat(myformat)
        finalDate = myCalender.time.time
        Dateedt.setText(sdf.format(myCalender.time))

        timeedt.visibility = View.VISIBLE
    }
}