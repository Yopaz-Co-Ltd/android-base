package com.kira.android_base.base.ui.widgets.fieldposteditor

import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.kira.android_base.R
import com.kira.android_base.base.ui.widgets.recyclerview.BaseRecyclerViewAdapter
import com.kira.android_base.databinding.ItemColorPickerBinding

class ColorPickerAdapter : BaseRecyclerViewAdapter<Int>(R.layout.item_color_picker) {
    var currentColor: Int = R.color.black
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.viewDataBinding as ItemColorPickerBinding?)?.apply {
            val backgroundColor = ContextCompat.getColor(colorItem.context, list[position])
            val originDrawable = GradientDrawable()
            originDrawable.shape = GradientDrawable.OVAL
            originDrawable.setColor(backgroundColor)
            colorItem.background = originDrawable
            colorItem.elevation = 10f

            colorItem.setOnClickListener {
                val previousColor = currentColor
                listener?.onItemClick(position, list[position])
                notifyItemChanged(list.indexOf(previousColor))
                notifyItemChanged(position)
                currentColor = list[position]
            }

            if (list[position] == currentColor) {
                colorItem.setImageResource(R.drawable.ic_tick)
            } else {
                colorItem.setImageDrawable(null)
            }
        }
    }
}

