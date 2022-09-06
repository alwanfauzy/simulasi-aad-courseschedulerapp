package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.list.ListViewModelFactory
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var addCourseViewModel: AddCourseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        setupViewModel()
        setupTimePicker()
    }

    private fun setupViewModel() {
        val factory = ListViewModelFactory.createFactory(this)
        addCourseViewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]
    }

    private fun setupTimePicker() {
        findViewById<ImageButton>(R.id.btn_add_start_time).setOnClickListener {
            val dialogTimePicker = TimePickerFragment()
            dialogTimePicker.show(supportFragmentManager, START_TIME_PICKER)
        }

        findViewById<ImageButton>(R.id.btn_add_end_time).setOnClickListener {
            val dialogTimePicker = TimePickerFragment()
            dialogTimePicker.show(supportFragmentManager, END_TIME_PICKER)
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        if (tag == START_TIME_PICKER)
            findViewById<TextView>(R.id.tv_add_start_time).text = dateFormat.format(calendar.time)
        else
            findViewById<TextView>(R.id.tv_add_end_time).text = dateFormat.format(calendar.time)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                insertCourse()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun insertCourse() {
        val courseName =
            findViewById<TextInputEditText>(R.id.edit_add_course_name).text?.trim().toString()
        val day = findViewById<Spinner>(R.id.spinner_add_day).selectedItemPosition
        val startTime = findViewById<TextView>(R.id.tv_add_start_time).text.toString()
        val endTime = findViewById<TextView>(R.id.tv_add_end_time).text.toString()
        val lecturer =
            findViewById<TextInputEditText>(R.id.edit_add_lecturer).text?.trim().toString()
        val note = findViewById<TextInputEditText>(R.id.edit_add_note).text?.trim().toString()

        val isFilledBeforeInsert =
            courseName.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty() && lecturer.isNotEmpty() && note.isNotEmpty()

        if (isFilledBeforeInsert) {
            addCourseViewModel.insertCourse(
                courseName = courseName,
                day = day,
                startTime = startTime,
                endTime = endTime,
                lecturer = lecturer,
                note = note
            )
            finish()
        } else {
            Toast.makeText(this, R.string.input_empty_message, Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        private const val START_TIME_PICKER = "start_time_picker"
        private const val END_TIME_PICKER = "end_time_picker"
    }
}
