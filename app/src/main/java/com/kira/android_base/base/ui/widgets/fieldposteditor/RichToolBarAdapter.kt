package com.kira.android_base.base.ui.widgets.fieldposteditor

import com.kira.android_base.R
import com.kira.android_base.base.ui.widgets.recyclerview.BaseRecyclerViewAdapter
import com.kira.android_base.databinding.ItemRichToolBarBinding

class RichToolBarAdapter: BaseRecyclerViewAdapter<RichToolBarItemModel>(R.layout.item_rich_tool_bar) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.viewDataBinding as ItemRichToolBarBinding?)?.apply {
            btnRichToolBar.contentDescription = list[position].descriptionItem
            btnRichToolBar.setImageResource(list[position].imageSrc)

            btnRichToolBar.setOnClickListener {
                listener?.onItemClick(position, list[position])
            }


        }
    }
}
