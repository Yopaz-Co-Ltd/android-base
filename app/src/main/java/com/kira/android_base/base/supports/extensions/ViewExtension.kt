package com.kira.android_base.base.supports.extensions

import android.view.View

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
