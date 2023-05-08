package com.kira.android_base.main.fragments.home

import com.kira.android_base.R
import com.kira.android_base.base.ui.widgets.recyclerview.BaseRecyclerViewAdapter
import com.kira.android_base.databinding.ItemHomeBaseRecyclerViewBinding

class HomeRecyclerViewAdapter :
    BaseRecyclerViewAdapter<String>(R.layout.item_home_base_recycler_view) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.viewDataBinding as ItemHomeBaseRecyclerViewBinding?)?.apply {
            tvName.text = list[position]
            executePendingBindings()
        }
    }
}
