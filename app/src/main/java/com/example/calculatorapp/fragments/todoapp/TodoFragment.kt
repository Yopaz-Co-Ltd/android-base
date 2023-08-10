package com.example.calculatorapp.fragments.todoapp

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calculatorapp.*
import com.example.calculatorapp.fragments.FileHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class TodoFragment : Fragment(R.layout.fragment_todo) {
    private var todoListView: RecyclerView? = null
    private var emptyData: LinearLayout? = null
    private var currentNotificationID: Int = 0

    private var listTodoData: MutableList<ItemData> = mutableListOf()
    private val today = Calendar.getInstance()
    private var currentTitle: String? = null
    private var dayOfMonthPicked: Int = today.get(Calendar.DAY_OF_MONTH)
    private var monthPicked: Int = today.get(Calendar.MONTH)
    private var yearPicked: Int = today.get(Calendar.YEAR)
    private var hourOfDayPicked: Int = today.get(Calendar.HOUR_OF_DAY)
    private var minutePicked: Int = today.get(Calendar.MINUTE)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoListView = view.findViewById(R.id.todo_list_view)
        emptyData = view.findViewById(R.id.empty_data)

        listTodoData = FileHelper().readData(requireContext())
        isEmptyData()

        createNotificationChannel()

        val adapter = TodoListRVAdapter(listTodoData, object : RvInterface {
            override fun onRemoveItem(pos: Int) {
                cancelTodo(pos)
            }
        })

        todoListView?.adapter = adapter

        todoListView?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        view.findViewById<FloatingActionButton>(R.id.btn_dialog_add).setOnClickListener {
            showAddTodoDialog(adapter)
        }
    }

    private fun showAddTodoDialog(adapter: TodoListRVAdapter) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.add_todo_dialog_layout, null)
        var inputTodo: EditText? = dialogLayout.findViewById(R.id.input_todo_dialog)
        var btnDateDialog: Button = dialogLayout.findViewById(R.id.btn_date)
        var btnTimeDialog: Button = dialogLayout.findViewById(R.id.btn_time)
        btnDateDialog.text = "$dayOfMonthPicked/${monthPicked + 1}/$yearPicked"
        btnTimeDialog.text =
            "$hourOfDayPicked:${if (minutePicked < 10) "0$minutePicked" else "$minutePicked"}"

        btnTimeDialog.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    hourOfDayPicked = hourOfDay
                    minutePicked = minute
                    val formattedMinute = if (minute < 10) "0$minute" else "$minute"
                    btnTimeDialog.setText("$hourOfDayPicked:$formattedMinute")
                },
                hourOfDayPicked,
                minutePicked,
                true,
            ).show()
        }

        btnDateDialog.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    yearPicked = year
                    monthPicked = month
                    dayOfMonthPicked = dayOfMonth
                    btnDateDialog.setText("$dayOfMonthPicked/${monthPicked + 1}/$yearPicked")
                },
                yearPicked,
                monthPicked,
                dayOfMonthPicked
            ).show()
        }

        with(builder) {
            setPositiveButton("OK") { dialog, with ->
                currentTitle = inputTodo?.text.toString()
                addTodo(adapter)
            }
            setNegativeButton("Cancel") { _, _ -> }
            setView(dialogLayout)
            show()
        }
    }

    private fun addTodo(adapter: TodoListRVAdapter) {
        val datePicked = "$dayOfMonthPicked/${monthPicked + 1}/$yearPicked"
        val timePicked = "$hourOfDayPicked:$minutePicked"

        if (currentTitle != "") {
            scheduleNotification()
            listTodoData.add(
                0,
                ItemData(currentTitle!!, datePicked, timePicked, currentNotificationID)
            )
            FileHelper().writeData(listTodoData, requireContext())
            adapter.notifyDataSetChanged()
        }
        isEmptyData()
    }

    private fun cancelTodo(pos: Int) {
        val cancelItemTitle = listTodoData[pos].name
        AlertDialog.Builder(requireContext())
            .setTitle("Have you completed the task  $cancelItemTitle ?")
            .setMessage(
                "Are you sure you want to remove $cancelItemTitle ?"
            )
            .setPositiveButton("REMOVE") { _, _ ->
                val alarmManager =
                    requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val cancelAlarmID = listTodoData[pos].notificationId
                val cancelIntent = Intent(requireContext(), NotificationReceiver::class.java)
                val cancelPendingIntent = PendingIntent.getBroadcast(
                    requireContext(),
                    cancelAlarmID,
                    cancelIntent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                alarmManager.cancel(cancelPendingIntent)
                cancelPendingIntent.cancel()

                listTodoData.removeAt(pos)
                FileHelper().writeData(listTodoData, requireContext().applicationContext)
                todoListView?.adapter?.notifyDataSetChanged()
                isEmptyData()
            }
            .setNegativeButton("CANCEL") { _, _ -> }
            .show()
    }

    private fun scheduleNotification() {
        val calendar = Calendar.getInstance()
        calendar.set(yearPicked, monthPicked, dayOfMonthPicked, hourOfDayPicked, minutePicked)
        val time = calendar.timeInMillis
        val intent = Intent(requireContext(), NotificationReceiver::class.java)
        val getNotificationID = System.currentTimeMillis().toInt()

        val title = "Nhắc nhở"
        val message = currentTitle
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            getNotificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        currentNotificationID = getNotificationID

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    private fun createNotificationChannel() {
        val name = "Notification Channel"
        val description = "A Description of channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelID, name, importance)
            channel.description = description
            val notificationManager =
                requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    private fun isEmptyData() {
        if (listTodoData.isEmpty()) {
            emptyData?.visibility = View.VISIBLE
        } else {
            emptyData?.visibility = View.GONE
        }
    }

}

