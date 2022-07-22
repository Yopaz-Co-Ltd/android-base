package com.example.android_base_compose.base.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android_base_compose.base.database.daos.UserDao
import com.example.android_base_compose.base.database.entities.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}
