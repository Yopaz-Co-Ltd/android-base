package com.kira.android_base.base.repository.auth

import android.content.Context
import com.kira.android_base.R
import com.kira.android_base.base.Error
import com.kira.android_base.base.Result
import com.kira.android_base.base.api.auth.AuthApi
import com.kira.android_base.base.api.auth.models.login.LoginRequestModel
import com.kira.android_base.base.api.callApi
import com.kira.android_base.base.database.daos.UserDao
import com.kira.android_base.base.database.insertDatabase
import com.kira.android_base.base.database.runDatabaseTask
import com.kira.android_base.base.sharedpreferences.SharedPreferences
import com.kira.android_base.base.supports.utils.Constants
import com.kira.android_base.base.supports.wrapEspressoIdlingResource
import com.kira.android_base.base.toResult
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sharedPreferences: SharedPreferences,
    private val authApi: AuthApi,
    private val userDao: UserDao
) : AuthRepository {

    override fun getLocalAccessToken() =
        sharedPreferences.getString(Constants.ACCESS_TOKEN_KEY, Constants.DEFAULT_STRING)

    override suspend fun login(email: String, password: String): Result<Boolean> =
        wrapEspressoIdlingResource {
            val result = callApi { authApi.login(LoginRequestModel(email, password)) }
            val accessToken = result?.data?.accessToken ?: return@wrapEspressoIdlingResource Error(
                Error.Companion.Code.UNAUTHORIZED.value, context.getString(
                    R.string.error_login_failed
                )
            ).toResult<Boolean>()
            sharedPreferences.putString(Constants.ACCESS_TOKEN_KEY, accessToken)
            result.data.user?.let { user -> insertDatabase(userDao, user) }
            return@wrapEspressoIdlingResource true.toResult()
        }

    override suspend fun logout(): Result<Int> = wrapEspressoIdlingResource {
        sharedPreferences.remove(Constants.ACCESS_TOKEN_KEY)
        runDatabaseTask { userDao.deleteAllUser() }
    }
}
