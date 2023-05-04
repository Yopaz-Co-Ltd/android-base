package com.kira.android_base.base.repository.user

import com.kira.android_base.base.Result
import com.kira.android_base.base.database.entities.User

interface UserRepository {

    suspend fun getLocalUser(): Result<User?>
}
