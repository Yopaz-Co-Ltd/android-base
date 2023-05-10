package com.kira.android_base.base.sharedpreferences

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SharedPreferencesModule {

    @Singleton
    @Binds
    fun bindSharedPreferences(defaultSharedPreferences: DefaultSharedPreferences): SharedPreferences
}
