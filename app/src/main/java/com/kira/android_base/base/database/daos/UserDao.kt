package com.kira.android_base.base.database.daos

import androidx.room.Dao
import com.kira.android_base.base.database.entities.User

@Dao
abstract class UserDao : BaseDao<User>()
