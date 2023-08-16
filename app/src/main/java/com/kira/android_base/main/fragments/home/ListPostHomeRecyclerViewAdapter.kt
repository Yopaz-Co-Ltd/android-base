package com.kira.android_base.main.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kira.android_base.R

class ListPostHomeRecyclerViewAdapter(var data: List<PostItemHomeData>, val onClick: ListPostHomeRecyclerViewInterface) :
    RecyclerView.Adapter<ListPostHomeRecyclerViewAdapter.ListPostHomeViewHolder>() {

    private var txtUser: TextView? = null
    private var txtPlace: TextView? = null
    private var txtTime: TextView? = null
    private var txtTitle: TextView? = null

    inner class ListPostHomeViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPostHomeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_recycler_view, parent, false)
        return ListPostHomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListPostHomeViewHolder, position: Int) {
        holder.itemView.apply {
            txtUser = this.findViewById(R.id.user_item_home)
            txtPlace = this.findViewById(R.id.place_item_home)
            txtTime = this.findViewById(R.id.time_item_home)
            txtTitle = this.findViewById(R.id.title_item_home)
            txtUser?.text = data[position].user
            txtPlace?.text = data[position].place
            txtTime?.text = data[position].time
            txtTitle?.text = data[position].title
        }

        holder.itemView.findViewById<LinearLayout>(R.id.post_item_home).setOnClickListener{
            onClick.navigateToPostDetail(position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
