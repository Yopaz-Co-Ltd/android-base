package com.kira.android_base.base.ui.widgets.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T>(
    private val itemLayoutResId: Int
) : RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder>() {

    var list = mutableListOf<T>()
    var listener: Listener<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                itemLayoutResId,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { listener?.onItemClick(position, list[position]) }
        holder.itemView.setOnLongClickListener {
            listener?.onItemClick(position, list[position])
            false
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    interface Listener<T> {
        fun onItemClick(position: Int, t: T) {}
        fun onItemLongClick(position: Int, t: T) {}
    }
}
