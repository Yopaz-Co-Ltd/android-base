package com.kira.android_base.base.sharedpreference

import android.content.Context

class SharedPreferences(context: Context) {

    companion object {
        private const val APP_SHARED_PREFS_NAME = "shared_preferences"
    }

    val sharedPreferences =
        context.getSharedPreferences(APP_SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    fun saveData(key: String, value: Any) {
        when (value) {
            is String -> {
                sharedPreferences.edit().putString(key, value).apply()
            }
            is Int -> {
                sharedPreferences.edit().putInt(key, value).apply()
            }
            is Long -> {
                sharedPreferences.edit().putLong(key, value).apply()
            }
            is Boolean -> {
                sharedPreferences.edit().putBoolean(key, value).apply()
            }
            is Float -> {
                sharedPreferences.edit().putFloat(key, value).apply()
            }
        }
    }

    inline fun <reified T> getData(key: String, defaultValue: T? = null): Any? {
        return when (T::class) {
            String::class -> {
                sharedPreferences.getString(key, defaultValue?.toString())
            }
            Int::class -> {
                sharedPreferences.getInt(key, (defaultValue ?: 0) as Int)
            }
            Long::class -> {
                sharedPreferences.getLong(key, (defaultValue ?: 0L) as Long)
            }
            Boolean::class -> {
                sharedPreferences.getBoolean(key, (defaultValue ?: false) as Boolean)
            }
            Float::class -> {
                sharedPreferences.getFloat(key, (defaultValue ?: 0f) as Float)
            }
            else -> null
        }
    }
}
