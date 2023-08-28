package com.kira.android_base.base.ui.widgets.fieldposteditor

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import com.kira.android_base.R
import com.kira.android_base.base.ui.widgets.recyclerview.BaseRecyclerViewAdapter
import com.kira.android_base.databinding.ItemColorPickerBinding

class BGColorPickerAdapter : BaseRecyclerViewAdapter<Int>(R.layout.item_color_picker) {
    var currentBGColor: Int = R.color.white
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.viewDataBinding as ItemColorPickerBinding?)?.apply {
            val backgroundColor = ContextCompat.getColor(colorItem.context, list[position])
            val originDrawable = GradientDrawable()
            originDrawable.shape = GradientDrawable.RECTANGLE
            originDrawable.setColor(backgroundColor)
            colorItem.elevation = 10f
            colorItem.background = originDrawable

            colorItem.setOnClickListener {
                val previousColor = currentBGColor
                listener?.onItemClick(position, list[position])
                notifyItemChanged(list.indexOf(previousColor))
                notifyItemChanged(position)
                currentBGColor = list[position]
            }

            if (list[position] == currentBGColor) {
                if (list[position] == R.color.white) {
                    originDrawable.setStroke(8, Color.BLACK)
                } else {
                    originDrawable.setStroke(8, Color.WHITE)
                }
                colorItem.background = originDrawable
            } else {
                colorItem.background = originDrawable
            }
        }
    }
}
