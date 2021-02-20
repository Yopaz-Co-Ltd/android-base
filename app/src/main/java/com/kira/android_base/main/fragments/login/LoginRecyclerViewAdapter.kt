package com.kira.android_base.main.fragments.login

import com.kira.android_base.R
import com.kira.android_base.base.ui.widgets.recyclerview.BaseRecyclerViewAdapter
import com.kira.android_base.databinding.ItemLoginBaseRecyclerViewBinding

class LoginRecyclerViewAdapter :
    BaseRecyclerViewAdapter<String>(R.layout.item_login_base_recycler_view) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.viewDataBinding as ItemLoginBaseRecyclerViewBinding?)?.apply {
            tvName.text = list[position]
            executePendingBindings()
        }
    }
}
