package com.kira.android_base.base.repository

import com.kira.android_base.base.api.APIs
import com.kira.android_base.base.api.models.response.BaseResponse
import com.kira.android_base.base.datahandling.Error
import com.kira.android_base.base.datahandling.toResult
import com.kira.android_base.base.dispatcher.IODispatcher
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apIs: APIs,
    private val moshi: Moshi,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) {

    private fun Throwable.toError(): Error {
        return when (this) {
            is SocketTimeoutException, is UnknownHostException, is ConnectException -> Error(
                Error.Companion.Code.NETWORK.value.code,
                localizedMessage ?: toString()
            )
            else -> Error.Companion.Code.DEFAULT.value
        }
    }

    private suspend fun <T> dispatchCoroutineContext(function: suspend () -> Response<BaseResponse<T>>) =
        withContext(dispatcher) {
            runCatching {
                val response = function()
                response.body()?.let { return@withContext it.data?.toResult() }

                return@withContext response.errorBody()?.let {
                    moshi.adapter(Error::class.java)
                        .fromJson(it.string())
                        ?.apply {
                            this.code = response.code()
                        }
                }?.toResult()
            }.getOrElse { it.toError() }.toResult()
        }

    suspend fun login() = dispatchCoroutineContext { apIs.login() }
}
