package com.kira.android_base.base.repository.user

import com.kira.android_base.base.database.daos.UserDao
import com.kira.android_base.base.database.runDatabaseTask

class DefaultUserRepository(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getLocalUser() = runDatabaseTask {
        userDao.getUser()
    }
}
