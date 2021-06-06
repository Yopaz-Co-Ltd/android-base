package com.kira.android_base.base.supports.utils

import android.content.res.Resources
import com.kira.android_base.R

fun Resources.getStatusBarHeight(): Int {
    try {
        val statusBarResourceId = getIdentifier("status_bar_height", "dimen", "android")
        if (statusBarResourceId > 0) return getDimensionPixelSize(statusBarResourceId)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return getDimensionPixelOffset(R.dimen.space_24)
}
