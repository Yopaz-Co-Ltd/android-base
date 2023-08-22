package com.kira.android_base.main.fragments.post_detai

import com.kira.android_base.R
import com.kira.android_base.base.ui.widgets.recyclerview.BaseRecyclerViewAdapter
import com.kira.android_base.databinding.ItemHomeRecyclerViewBinding
import com.kira.android_base.main.fragments.home.PostItemHomeModel

class SuggestionPostRecyclerViewAdapter :
    BaseRecyclerViewAdapter<PostItemHomeModel>(R.layout.item_home_recycler_view) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.viewDataBinding as ItemHomeRecyclerViewBinding?)?.apply {
            userItemHome.text = list[position].user
            placeItemHome.text = list[position].place
            timeItemHome.text = list[position].time
            titleItemHome.text = list[position].title
        }
        holder.viewDataBinding.postItemHome.setOnClickListener {
            listener?.onItemClick(position, list[position])
        }
    }
}
