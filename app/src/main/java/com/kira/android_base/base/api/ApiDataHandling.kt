package com.kira.android_base.base.api

import com.kira.android_base.base.Error
import com.kira.android_base.base.toError
import com.kira.android_base.base.toResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> callApi(function: suspend () -> Response<BaseResponse<T>>) =
    withContext(Dispatchers.IO) {
        runCatching {
            val response = function()
            response.body()?.let { return@withContext it.data?.toResult() }

            return@withContext response.errorBody()?.let {
                provideMoshi().adapter(Error::class.java)
                    .fromJson(it.string())
                    ?.apply {
                        this.code = response.code()
                    }
            }?.toResult()
        }.getOrElse { it.toError() }.toResult()
    }
