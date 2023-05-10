package com.kira.android_base.base.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

const val TEST_APP_DATABASE_NAME = "test_app_database"

@Module
@InstallIn(SingletonComponent::class)
object TestDatabaseModule {

    @Provides
    @Named(TEST_APP_DATABASE_NAME)
    fun provideRoomDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}
