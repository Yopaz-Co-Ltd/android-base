package com.kira.android_base.base.supports.utils

import android.content.res.Resources

fun Resources.getStatusBarHeight(): Int? {
    try {
        val statusBarResourceId = getIdentifier("status_bar_height", "dimen", "android")
        if (statusBarResourceId > 0) return getDimensionPixelSize(statusBarResourceId)
        return null
    } catch (e: Exception) {
        return null
    }
}
