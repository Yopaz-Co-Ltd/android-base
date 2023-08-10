package com.example.calculatorapp.fragments.todoapp

import android.app.PendingIntent
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calculatorapp.ItemData
import com.example.calculatorapp.R
import com.example.calculatorapp.RvInterface

class TodoListRVAdapter(var data: List<ItemData>, val onClick: RvInterface) :
    RecyclerView.Adapter<TodoListRVAdapter.TodoViewHolder>() {

    private lateinit var txtName: TextView
    private lateinit var txtDate: TextView
    private lateinit var txtTime: TextView
    private var notificationId: Int? = null

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    //ctrl + i
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo_list, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.itemView.apply {
            txtName = this.findViewById(R.id.todo_item_name)
            txtDate = this.findViewById(R.id.todo_item_date)
            txtTime = this.findViewById(R.id.todo_item_time)
            txtName.text = data[position].name
            txtTime.text = data[position].time
            txtDate.text = data[position].date
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
