package com.kira.android_base.base.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kira.android_base.base.database.daos.UserDao
import com.kira.android_base.base.database.entities.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
