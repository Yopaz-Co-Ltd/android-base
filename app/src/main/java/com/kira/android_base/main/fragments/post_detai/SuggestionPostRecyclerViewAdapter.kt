package com.kira.android_base.main.fragments.post_detai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kira.android_base.R
import com.kira.android_base.main.fragments.home.ListPostHomeRecyclerViewInterface
import com.kira.android_base.main.fragments.home.PostItemHomeData

class SuggestionPostRecyclerViewAdapter(var data: List<SuggestionPostModel>) :
    RecyclerView.Adapter<SuggestionPostRecyclerViewAdapter.SuggestionPostViewHolder>() {

    private var txtUser: TextView? = null
    private var txtPlace: TextView? = null
    private var txtTime: TextView? = null
    private var txtTitle: TextView? = null

    inner class SuggestionPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionPostViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_recycler_view, parent, false)
        return SuggestionPostViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuggestionPostViewHolder, position: Int) {
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
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
