package com.kira.android_base.main.fragments.login

import com.kira.android_base.base.database.entities.User
import com.kira.android_base.base.datahandling.Result
import com.kira.android_base.base.datahandling.toResult
import com.kira.android_base.base.dispatcher.IODispatcher
import com.kira.android_base.base.repository.LocalDataSource
import com.kira.android_base.base.repository.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun login(): Result<User?>? = withContext(dispatcher) {
        val result = remoteDataSource.login()
        result?.data?.let { user ->
            val insertLocalUserResult = localDataSource.insertUser(user)
            insertLocalUserResult.data?.let {
                return@withContext user.toResult()
            }

            insertLocalUserResult.error?.let { error ->
                return@withContext error.toResult()
            }
        }

        return@withContext result?.error?.toResult<User?>()
    }
}
