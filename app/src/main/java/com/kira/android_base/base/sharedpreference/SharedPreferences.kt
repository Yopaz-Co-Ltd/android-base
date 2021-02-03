package com.kira.android_base.base.sharedpreference

import android.content.Context

class SharedPreferences(context: Context) {

    companion object {
        private const val APP_SHARED_PREFS_NAME = "shared_preferences"
    }

    private val sharedPreferences =
        context.getSharedPreferences(APP_SHARED_PREFS_NAME, Context.MODE_PRIVATE)
}
