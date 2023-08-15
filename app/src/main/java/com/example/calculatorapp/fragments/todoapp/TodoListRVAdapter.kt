package com.example.calculatorapp.fragments.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calculatorapp.R
import com.example.calculatorapp.RecyclerViewInterface
import com.example.calculatorapp.TaskModel

class TodoListRVAdapter(var data: List<TaskModel>, val onClick: RecyclerViewInterface) :
    RecyclerView.Adapter<TodoListRVAdapter.TodoViewHolder>() {

    private var txtName: TextView? = null
    private var txtDate: TextView? = null
    private var txtTime: TextView? = null
    private var notificationId: Int? = null

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo_list, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.itemView.apply {
            txtName = findViewById(R.id.todo_item_name)
            txtDate = findViewById(R.id.todo_item_date)
            txtTime = findViewById(R.id.todo_item_time)
            txtName?.text = data[position].name
            txtTime?.text = data[position].time
            txtDate?.text = data[position].date
            notificationId = data[position].notificationId

            holder.itemView.findViewById<ImageButton>(R.id.btn_remove_item).setOnClickListener {
                onClick.onRemoveItem(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
