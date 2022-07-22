package com.example.android_base_compose.base.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.android_base_compose.base.database.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * FROM user")
    fun getAllUser(): Flow<User?>
}
