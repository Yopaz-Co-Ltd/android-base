package com.kira.android_base.base.ui.widgets.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class DataBindingListAdapter<MODEL : Any, DATA_BINDING_CLASS : ViewDataBinding>(
    private val inflaterMethod: (
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        attachToRoot: Boolean
    ) -> DATA_BINDING_CLASS
) : ListAdapter<MODEL, DataBindingListAdapter.DataBindingViewHolder<DATA_BINDING_CLASS>>(
    BaseCallbackItem<MODEL>()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataBindingViewHolder(
            inflaterMethod(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            onCreateItem(this.binding)
        }

    final override fun onBindViewHolder(
        holder: DataBindingViewHolder<DATA_BINDING_CLASS>,
        position: Int
    ) {
        onBind(holder.binding, getItem(position), position)
    }

    open fun onCreateItem(binding: DATA_BINDING_CLASS) {}

    open class DataBindingViewHolder<DATA_BINDING_CLASS : ViewDataBinding>(val binding: DATA_BINDING_CLASS) :
        RecyclerView.ViewHolder(binding.root)

    abstract fun onBind(binding: DATA_BINDING_CLASS, item: MODEL, position: Int)
}
