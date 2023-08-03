package com.example.calculatorapp.fragments.todoapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calculatorapp.ItemData
import com.example.calculatorapp.R
import com.example.calculatorapp.RvInterface
import java.util.*

class TodoFragment : Fragment(R.layout.fragment_todo) {
    private lateinit var inputTodo: EditText
    private lateinit var todoListView: RecyclerView
    private lateinit var btnTime: Button
    private lateinit var btnDate: Button
    private var listTodoData: MutableList<ItemData> = mutableListOf()
    val today = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputTodo = view.findViewById(R.id.input_text)
        todoListView = view.findViewById(R.id.todo_list_view)
        btnDate = view.findViewById(R.id.btn_date)
        btnTime = view.findViewById(R.id.btn_time)
        val startHour = today.get(Calendar.HOUR_OF_DAY)
        val startMinute = today.get(Calendar.MINUTE)
        val startDate = today.get(Calendar.DAY_OF_MONTH)
        val startMonth = today.get(Calendar.MONTH)
        val startYear = today.get(Calendar.YEAR)

        btnDate.setText("$startDate/${startMonth + 1}/$startYear")
        btnTime.setText("$startHour:$startMinute")

        val adapter = TodoListRVAdapter(listTodoData, object : RvInterface {
            override fun onRemoveItem(pos: Int) {
                listTodoData.removeAt(pos)
                todoListView.adapter?.notifyDataSetChanged()
            }
        })

        todoListView.adapter = adapter

        todoListView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        view.findViewById<Button>(R.id.btn_add).setOnClickListener {
            addTodo(adapter)
        }

        btnTime.setOnClickListener{
            TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                btnTime.setText("$hourOfDay:$minute")
            },startHour,startMinute, true).show()
        }

        btnDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    btnDate.setText("$dayOfMonth/${month + 1}/$year")
                },
                startYear,
                startMonth,
                startDate
            ).show()
        }
    }

    fun addTodo(adapter: TodoListRVAdapter) {
        val inputText = inputTodo.text.toString()
        val datePicked = btnDate.text.toString()
        val timePicked = btnTime.text.toString()
        val dateTimePicked = "$datePicked - $timePicked"
        if (inputText != "") {
            listTodoData.add(0,ItemData(inputText, dateTimePicked))
            inputTodo.setText("")
            adapter.notifyDataSetChanged()
        }
    }

}
