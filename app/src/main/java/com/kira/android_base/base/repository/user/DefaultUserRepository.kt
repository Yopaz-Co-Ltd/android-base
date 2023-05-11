package com.kira.android_base.base.repository.user

import com.kira.android_base.base.database.daos.UserDao
import com.kira.android_base.base.database.runDatabaseTask
import com.kira.android_base.base.supports.wrapEspressoIdlingResource
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getLocalUser() = wrapEspressoIdlingResource {
        runDatabaseTask {
            userDao.getUser()
        }
    }
}
