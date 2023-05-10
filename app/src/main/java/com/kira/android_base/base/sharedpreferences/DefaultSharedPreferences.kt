package com.kira.android_base.base.sharedpreferences

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultSharedPreferences @Inject constructor(
    @ApplicationContext context: Context
) : SharedPreferences {

    companion object {
        private const val APP_SHARED_PREFS_NAME = "shared_preferences"
    }

    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        context,
        APP_SHARED_PREFS_NAME,
        MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun getString(key: String, defaultValue: String): String? {
        return encryptedSharedPreferences.getString(key, defaultValue)
    }

    override fun putString(key: String, value: String) {
        encryptedSharedPreferences.edit().putString(key, value).apply()
    }

    override fun remove(key: String) {
        encryptedSharedPreferences.edit().remove(key).apply()
    }
}
