package com.example.android_base_compose.base.module

import android.content.Context
import androidx.room.Room
import com.example.android_base_compose.base.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val APP_DB_NAME = "application.database"

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, APP_DB_NAME)
            .fallbackToDestructiveMigration().build()
}
