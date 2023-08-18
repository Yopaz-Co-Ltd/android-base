package com.kira.android_base.main.fragments.post_editor

import androidx.recyclerview.widget.RecyclerView
import com.kira.android_base.R
import com.kira.android_base.base.ui.widgets.recyclerview.BaseRecyclerViewAdapter
import com.kira.android_base.databinding.ItemTagRecyclerViewBinding

class TagListRecyclerViewAdapter :
    BaseRecyclerViewAdapter<String>(R.layout.item_tag_recycler_view) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.viewDataBinding as ItemTagRecyclerViewBinding?)?.apply {
            tagTitle.text = list[position]

            tagTitle.setOnClickListener {
                val currentPosition = holder.adapterPosition
                if (currentPosition != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(currentPosition, list[currentPosition])
                }
            }
        }
    }

    fun updateInsertData(currentTagInput: String) {
        this.apply {
            list.add(itemCount, currentTagInput)
            notifyItemInserted(itemCount)
        }
    }

    fun updateRemoveData(position: Int) {
        this.apply {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
