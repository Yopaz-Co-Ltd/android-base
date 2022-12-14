package com.kira.android_base.base.ui.widgets.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BaseRecycleViewAdapterWithoutViewBinding<MODEL : Any>(@LayoutRes val itemResource: Int) :
    ListAdapter<MODEL, BaseRecycleViewAdapterWithoutViewBinding.BaseViewHolder>(BaseCallbackItem<MODEL>()) {

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BaseViewHolder(
            LayoutInflater.from(parent.context).inflate(itemResource, parent, false)
        )

    final override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBind(holder.view, getItem(position))
    }

    abstract fun onBind(view: View, item: MODEL)

    class BaseViewHolder constructor(val view: View) : ViewHolder(view)
}

open class BaseCallbackItem<ITEM : Any> : DiffUtil.ItemCallback<ITEM>() {

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ITEM, newItem: ITEM) =
        oldItem.toString() == newItem.toString()

    override fun areItemsTheSame(oldItem: ITEM, newItem: ITEM) = oldItem === newItem
}
