package com.kira.android_base.base.supports.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.show(isShow: Boolean) {
    if (isShow) visible() else gone()
}

fun View.setMargin(
    left: Int = marginLeft,
    top: Int = marginTop,
    right: Int = marginRight,
    bottom: Int = marginBottom
) {
    val marginLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    marginLayoutParams.setMargins(left, top, right, bottom)
    this.layoutParams = marginLayoutParams
}
