package com.kira.android_base.base.database

import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { provideRoomDb(androidContext()) }
    single { provideUserDao(get()) }
}

private const val APP_DB_NAME = "app_database"

private fun provideRoomDb(context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, APP_DB_NAME)
        .fallbackToDestructiveMigration()
        .build()

private fun provideUserDao(appDatabase: AppDatabase) = appDatabase.userDao()
