package com.kira.android_base.base.ui

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.NumberPicker
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.lang.reflect.Field

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, link: String) {
    Glide.with(imageView.context).load(link).into(imageView)
}

@BindingAdapter(
    value = ["max", "min", "default", "wrap_selector_wheel", "divider_color"],
    requireAll = false
)
fun setUpNumberPicker(
    numberPicker: NumberPicker,
    max: Int?,
    min: Int?,
    default: Int?,
    wrapSelectorWheel: Boolean = true,
    dividerColor: Int?
) {
    numberPicker.apply {
        max?.let { maxValue = it }
        min?.let { minValue = it }
        default?.let { value = it }
        this.wrapSelectorWheel = wrapSelectorWheel
        dividerColor?.let {
            try {
                val field: Field = NumberPicker::class.java.getDeclaredField("mSelectionDivider")
                field.isAccessible = true
                field.set(this, ColorDrawable(it))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

@BindingAdapter("enable")
fun setEnable(view: View, enable: Boolean = true) {
    view.isEnabled = enable
}

@BindingAdapter(
    value = ["corner", "top_left_corner", "top_right_corner", "bottom_left_corner", "bottom_right_corner", "background_color"],
    requireAll = false
)
fun setCorner(
    view: View,
    corner: Float?,
    topLeftCorner: Float?,
    topRightCorner: Float?,
    bottomLeftCorner: Float?,
    bottomRightCorner: Float?,
    backgroundColor: Int
) {
    view.background = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadii =
            floatArrayOf(
                topLeftCorner ?: 0f,
                topLeftCorner ?: 0f,
                topRightCorner ?: 0f,
                topRightCorner ?: 0f,
                bottomLeftCorner ?: 0f,
                bottomLeftCorner ?: 0f,
                bottomRightCorner ?: 0f,
                bottomRightCorner ?: 0f
            )
        corner?.let { cornerRadius = it }
        this.setColor(backgroundColor)
    }
}
