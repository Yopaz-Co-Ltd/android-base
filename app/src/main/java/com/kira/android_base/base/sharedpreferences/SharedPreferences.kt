package com.kira.android_base.base.sharedpreferences

interface SharedPreferences {

    fun getString(key: String, defaultValue: String): String?

    fun putString(key: String, value: String)

    fun remove(key: String)
}
