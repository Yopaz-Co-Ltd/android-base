package com.kira.android_base.main.fragments.post_preview

import com.kira.android_base.R
import com.kira.android_base.base.ui.widgets.recyclerview.BaseRecyclerViewAdapter
import com.kira.android_base.databinding.ItemTagRecyclerViewBinding

class PostPreviewAdapter : BaseRecyclerViewAdapter<String>(R.layout.item_tag_recycler_view) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.viewDataBinding as ItemTagRecyclerViewBinding?)?.apply {
            tagTitle.text = list[position]
        }
    }
}
