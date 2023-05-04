package com.kira.android_base.base.sharedpreference

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val APP_SHARED_PREFS_NAME = "shared_preferences"

val sharedPreferencesModule = module {
    single {
        val context = androidContext()
        EncryptedSharedPreferences.create(
            context,
            APP_SHARED_PREFS_NAME,
            MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}
