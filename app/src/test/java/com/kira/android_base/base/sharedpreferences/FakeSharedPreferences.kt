package com.kira.android_base.base.sharedpreferences

class FakeSharedPreferences : SharedPreferences {
    private val map = mutableMapOf<String, String>()

    override fun getString(key: String, defaultValue: String): String {
        return map[key] ?: defaultValue
    }

    override fun putString(key: String, value: String) {
        map[key] = value
    }

    override fun remove(key: String) {
        map.remove(key)
    }
}
