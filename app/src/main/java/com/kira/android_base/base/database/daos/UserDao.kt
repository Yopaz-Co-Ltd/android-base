package com.kira.android_base.base.database.daos

import androidx.room.Dao
import androidx.room.Query
import com.kira.android_base.base.database.entities.User

@Dao
abstract class UserDao : BaseDao<User>() {

    @Query("select * from user limit 1")
    abstract fun getUser(): User?

    @Query("delete from user")
    abstract fun deleteAllUser(): Int
}
