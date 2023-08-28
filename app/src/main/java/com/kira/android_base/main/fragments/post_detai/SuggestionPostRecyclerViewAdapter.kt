package com.kira.android_base.main.fragments.post_detai

import com.kira.android_base.R
import com.kira.android_base.base.ui.widgets.recyclerview.BaseRecyclerViewAdapter
import com.kira.android_base.databinding.ItemHomeRecyclerViewBinding
import com.kira.android_base.main.fragments.home.PostItemHomeModel

class SuggestionPostRecyclerViewAdapter :
    BaseRecyclerViewAdapter<PostItemHomeModel>(R.layout.item_home_recycler_view) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        (holder.viewDataBinding as ItemHomeRecyclerViewBinding?)?.apply {
            userItemHome.text = currentItem.user
            placeItemHome.text = currentItem.place
            timeItemHome.text = currentItem.time
            titleItemHome.text = currentItem.title
        }
        holder.viewDataBinding.postItemHome.setOnClickListener {
            listener?.onItemClick(position, list[position])
        }
    }
}
