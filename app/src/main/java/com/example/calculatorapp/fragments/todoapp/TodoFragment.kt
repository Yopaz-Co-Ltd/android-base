package com.example.calculatorapp.fragments.todoapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calculatorapp.*
import com.example.calculatorapp.NotificationReceiver.Companion.MESSAGE_EXTRA
import com.example.calculatorapp.NotificationReceiver.Companion.TITLE_EXTRA
import com.example.calculatorapp.fragments.FileUltis
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class TodoFragment : Fragment(R.layout.fragment_todo) {
    private var todoListView: RecyclerView? = null
    private var emptyData: LinearLayout? = null
    private var currentNotificationID: Int = 0

    private var listTodoData: List<TaskModel> = emptyList()
    private var mutableListTodoData: MutableList<TaskModel> = mutableListOf()
    private val today = Calendar.getInstance()
    private var currentTitle: String = ""
    private var dayOfMonthPicked: Int = today.get(Calendar.DAY_OF_MONTH)
    private var monthPicked: Int = today.get(Calendar.MONTH)
    private var yearPicked: Int = today.get(Calendar.YEAR)
    private var hourOfDayPicked: Int = today.get(Calendar.HOUR_OF_DAY)
    private var minutePicked: Int = today.get(Calendar.MINUTE)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoListView = view.findViewById(R.id.todo_list_view)
        emptyData = view.findViewById(R.id.empty_data)

        context?.let {
            mutableListTodoData = FileUltis.readData(it)
            listTodoData = mutableListTodoData
        }

        validateEmptyView()

        context?.let { createNotificationChannel(it) }

        val adapter = TodoListRVAdapter(listTodoData, object : RecyclerViewInterface {
            override fun onRemoveItem(position: Int) {
                cancelTodo(position)
            }
        })
        todoListView?.adapter = adapter
        todoListView?.layoutManager = context?.let {
            LinearLayoutManager(
                it, LinearLayoutManager.VERTICAL, false
            )
        }

        view.findViewById<FloatingActionButton>(R.id.btn_dialog_add).setOnClickListener {
            showAddTodoDialog(adapter)
        }
    }

    private fun showAddTodoDialog(adapter: TodoListRVAdapter) {

        val dialogLayout = layoutInflater.inflate(R.layout.add_todo_dialog_layout, null)
        val inputTodo: EditText? = dialogLayout.findViewById(R.id.input_todo_dialog)
        val btnDateDialog: Button = dialogLayout.findViewById(R.id.btn_date)
        val btnTimeDialog: Button = dialogLayout.findViewById(R.id.btn_time)
        btnDateDialog.text = getString(
            R.string.date_picked,
            dayOfMonthPicked,
            monthPicked + 1,
            yearPicked
        )
        btnTimeDialog.text = getString(
            R.string.time_picked,
            hourOfDayPicked,
            minutePicked
        )
        btnTimeDialog.setOnClickListener {
            context?.let {
                TimePickerDialog(
                    it,
                    { _, hourOfDay, minute ->
                        hourOfDayPicked = hourOfDay
                        minutePicked = minute
                        btnTimeDialog.setText(btnTimeDialog.text)
                    },
                    hourOfDayPicked,
                    minutePicked,
                    true,
                ).show()
            }
        }

        btnDateDialog.setOnClickListener {
            context?.let {
                DatePickerDialog(
                    it,
                    { _, year, month, dayOfMonth ->
                        yearPicked = year
                        monthPicked = month
                        dayOfMonthPicked = dayOfMonth
                        btnDateDialog.setText(btnDateDialog.text)
                    },
                    yearPicked,
                    monthPicked,
                    dayOfMonthPicked
                ).show()
            }
        }

        context?.let {
            val builder = AlertDialog.Builder(context)
            with(builder) {
                setPositiveButton(R.string.ok_text) { dialog, with ->
                    currentTitle = inputTodo?.text.toString()
                    addTodo(adapter)
                }
                setNegativeButton(R.string.cancel_text) { _, _ -> }
                setView(dialogLayout)
                show()
            }
        }
    }

    private fun addTodo(adapter: TodoListRVAdapter) {
        val datePicked = getString(
            R.string.date_picked,
            dayOfMonthPicked,
            monthPicked + 1,
            yearPicked
        )
        val timePicked = getString(
            R.string.time_picked,
            hourOfDayPicked,
            minutePicked
        )

        if (currentTitle != "") {
            scheduleNotification()
            mutableListTodoData.add(
                0, TaskModel(currentTitle, datePicked, timePicked, currentNotificationID)
            )
            context?.let {
                FileUltis.writeData(it, mutableListTodoData)
            }
            listTodoData = mutableListTodoData
            adapter.notifyDataSetChanged()
        }
        validateEmptyView()
    }

    private fun cancelTodo(position: Int) {
        val cancelItemTitle = listTodoData[position].name
        AlertDialog.Builder(context)
            .setTitle(getString(R.string.have_you_completed_the_task, cancelItemTitle)).setMessage(
                getString(R.string.have_you_completed_the_task, cancelItemTitle)
            ).setPositiveButton(R.string.remove_text) { _, _ ->
                val alarmManager = context?.let {
                    it.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                }
                val cancelAlarmID = listTodoData[position].notificationId
                context?.let {
                    val cancelIntent = Intent(it, NotificationReceiver::class.java)
                    val cancelPendingIntent = PendingIntent.getBroadcast(
                        it,
                        cancelAlarmID,
                        cancelIntent,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )
                    alarmManager?.cancel(cancelPendingIntent)
                    cancelPendingIntent.cancel()
                }

                mutableListTodoData.removeAt(position)
                context?.let {
                    FileUltis.writeData(it.applicationContext, mutableListTodoData)
                }
                listTodoData = mutableListTodoData
                todoListView?.adapter?.notifyDataSetChanged()
                validateEmptyView()
            }.setNegativeButton(R.string.cancel_text) { _, _ -> }.show()
    }

    private fun scheduleNotification() {
        val calendar = Calendar.getInstance()
        calendar.set(yearPicked, monthPicked, dayOfMonthPicked, hourOfDayPicked, minutePicked)
        val time = calendar.timeInMillis
        val getNotificationID = System.currentTimeMillis().toInt()
        val title = R.string.reminder_notification_title
        val message = currentTitle
        context?.let {
            val intent = Intent(it, NotificationReceiver::class.java)
            intent.putExtra(TITLE_EXTRA, title)
            intent.putExtra(MESSAGE_EXTRA, message)
            val pendingIntent = PendingIntent.getBroadcast(
                it,
                getNotificationID,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            currentNotificationID = getNotificationID

            val alarmManager = it.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, time, pendingIntent
            )
        }
    }

    private fun validateEmptyView() {
        if (listTodoData.isEmpty()) {
            emptyData?.visibility = View.VISIBLE
        } else {
            emptyData?.visibility = View.GONE
        }
    }

}

