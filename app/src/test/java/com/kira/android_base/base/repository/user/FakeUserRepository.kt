package com.kira.android_base.base.repository.user

import com.kira.android_base.base.Error
import com.kira.android_base.base.Result
import com.kira.android_base.base.database.entities.User
import com.kira.android_base.base.toResult

class FakeUserRepository : UserRepository {

    private var shouldShowGetUserError: Boolean = false
    private val user: User = User(1, "kira", 19)

    override suspend fun getLocalUser(): Result<User?> {
        if (shouldShowGetUserError) return Error(
            Error.Companion.Code.DEFAULT.value,
            "Get Local User From Database Failed"
        ).toResult()
        return user.toResult()
    }

    fun setShouldShowGetUserError(shouldShowGetUserError: Boolean) {
        this.shouldShowGetUserError = shouldShowGetUserError
    }
}
