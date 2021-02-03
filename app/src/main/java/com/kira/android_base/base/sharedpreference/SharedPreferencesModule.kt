package com.kira.android_base.base.sharedpreference

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPreferencesModule = module {
    single { SharedPreferences(androidContext()) }
}
