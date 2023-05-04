package com.kira.android_base.base.repository.auth

import com.kira.android_base.base.Result

interface AuthRepository {

    fun getLocalAccessToken(): String?

    suspend fun login(email: String, password: String): Result<Boolean>

    suspend fun logout(): Result<Int>
}
