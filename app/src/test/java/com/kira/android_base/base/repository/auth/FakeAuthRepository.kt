package com.kira.android_base.base.repository.auth

import com.kira.android_base.base.Error
import com.kira.android_base.base.Result
import com.kira.android_base.base.database.entities.User
import com.kira.android_base.base.toResult

class FakeAuthRepository : AuthRepository {

    companion object {
        const val ERROR_LOGIN_FAILED = "Login failed!"
        const val ERROR_LOGOUT_FAILED = "Logout failed!"
    }

    private var shouldShowLoginError = false
    private var shouldShowLogoutError = false
    private var accessToken: String? = null
    private var user: User? = null

    override fun getLocalAccessToken(): String? {
        return accessToken
    }

    override suspend fun login(email: String, password: String): Result<Boolean> {
        if (shouldShowLoginError) {
            return Error(Error.Companion.Code.UNAUTHORIZED.value, ERROR_LOGIN_FAILED).toResult()
        }
        accessToken = "fake acess token"
        user = User(1, "kira", 19)
        return true.toResult()
    }

    override suspend fun logout(): Result<Int> {
        accessToken = null
        if (shouldShowLogoutError)
            return Error(Error.Companion.Code.DEFAULT.value, ERROR_LOGOUT_FAILED).toResult()
        user = null
        return 1.toResult()
    }

    fun setShouldShowLoginError(shouldShowLoginError: Boolean) {
        this.shouldShowLoginError = shouldShowLoginError
    }

    fun setShouldShowLogoutError(shouldShowLogoutError: Boolean) {
        this.shouldShowLogoutError = shouldShowLogoutError
    }
}
