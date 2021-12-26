package com.kira.android_base.base.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SharedPreferences(context: Context) {

    companion object {
        private const val APP_SHARED_PREFS_NAME = "shared_preferences"

        const val DEFAULT_INT = -1
        const val DEFAULT_LONG = -1L
        const val DEFAULT_BOOLEAN = false
        const val DEFAULT_FLOAT = -1f
    }

    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        APP_SHARED_PREFS_NAME,
        MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

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
                sharedPreferences.getInt(key, (defaultValue ?: DEFAULT_INT) as Int)
            }
            Long::class -> {
                sharedPreferences.getLong(key, (defaultValue ?: DEFAULT_LONG) as Long)
            }
            Boolean::class -> {
                sharedPreferences.getBoolean(key, (defaultValue ?: DEFAULT_BOOLEAN) as Boolean)
            }
            Float::class -> {
                sharedPreferences.getFloat(key, (defaultValue ?: DEFAULT_FLOAT) as Float)
            }
            else -> null
        }
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
