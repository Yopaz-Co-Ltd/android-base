package com.kira.android_base.main.fragments.login

import com.kira.android_base.base.database.entities.User
import com.kira.android_base.base.repository.BaseRepository

class LoginRepository : BaseRepository() {

    fun login() = remoteDataSource.login()

    fun insertLocalUser(user: User) = localDataSource.insertUser(user)
}
