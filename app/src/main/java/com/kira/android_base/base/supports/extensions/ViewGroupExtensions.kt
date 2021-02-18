package com.kira.android_base.base.supports.extensions

import android.util.Log
import android.view.ViewGroup
import androidx.core.view.forEach

fun ViewGroup.logChildren() {
    forEach {
        Log.d(TAG, "logChildren: $it")
        if (it is ViewGroup) it.logChildren()
    }
}
