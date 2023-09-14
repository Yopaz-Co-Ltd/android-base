package com.kira.android_base.base.repository.auth

import android.content.Context
import android.content.SharedPreferences
import com.kira.android_base.R
import com.kira.android_base.base.Error
import com.kira.android_base.base.Result
import com.kira.android_base.base.api.auth.AuthApi
import com.kira.android_base.base.api.auth.models.login.LoginRequestModel
import com.kira.android_base.base.api.callApi
import com.kira.android_base.base.database.daos.UserDao
import com.kira.android_base.base.database.insertDatabase
import com.kira.android_base.base.database.runDatabaseTask
import com.kira.android_base.base.supports.utils.ACCESS_TOKEN_KEY
import com.kira.android_base.base.supports.utils.DEFAULT_STRING
import com.kira.android_base.base.toResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultAuthRepository(
    private val context: Context,
    private val sharedPreferences: SharedPreferences,
    private val authApi: AuthApi,
    private val userDao: UserDao
) : AuthRepository {

    override fun getLocalAccessToken() =
        sharedPreferences.getString(ACCESS_TOKEN_KEY, DEFAULT_STRING)

    override suspend fun login(email: String, password: String): Result<Boolean> =
        withContext(Dispatchers.IO) {
            val result = callApi { authApi.login(LoginRequestModel(email, password)) }
            val accessToken = result?.data?.accessToken ?: return@withContext Error(
                Error.Companion.Code.UNAUTHORIZED.value, context.getString(
                    R.string.error_login_failed
                )
            ).toResult<Boolean>()
            sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, accessToken).apply()
            result.data.user?.let { user -> insertDatabase(userDao, user) }
            return@withContext true.toResult()
        }

    override suspend fun logout(): Result<Int> {
        sharedPreferences.edit().remove(ACCESS_TOKEN_KEY).apply()
        return runDatabaseTask { userDao.deleteAllUser() }
    }
}
