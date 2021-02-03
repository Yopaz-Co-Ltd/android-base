package com.kira.android_base.base.datahandling

import com.squareup.moshi.Moshi
import io.reactivex.Observable
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Added onErrorReturn so when call fails, retrofit immediately returns Result object with Throwable
 * value and onNext is not called, so map function will also not be called.
 * Map will only be called if call succeed, not throwing any Throwables
 */
fun <T> Observable<T>.retrofitResponseToResult(): Observable<Result<T>> {
    return this.map { it.asResult() }
        .onErrorReturn {
            if (it is Exception) {
                return@onErrorReturn it.asErrorResult<T>()
            } else {
                throw it
            }
        }
}

fun <T> T.asResult(): Result<T> {
    return Result(data = this, error = null)
}

fun <T> Error.asResult(): Result<T> {
    return Result(data = null, error = this)
}

fun <T> Throwable.asErrorResult(): Result<T> {
    return Result(data = null, error = convertError(this))
}

private fun convertError(error: Throwable): Error {
    return when (error) {
        is HttpException -> parseErrorMessageFromServer(
            error.code(),
            error.response()?.errorBody()?.string().toString(),
            error.message()
        )
        is SocketTimeoutException, is UnknownHostException, is ConnectException -> Error(
            Error.Companion.Code.NETWORK.value.code,
            error.localizedMessage ?: error.toString()
        )
        else -> Error.Companion.Code.DEFAULT.value
    }
}

private fun parseErrorMessageFromServer(
    code: Int,
    response: String,
    defaultMessage: String
): Error {
    try {
        Moshi.Builder().build()
            .adapter(Error::class.java)
            .fromJson(response)?.apply {
                this.code = code
                return this
            }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return Error(
        Error.Companion.Code.DEFAULT.value.code,
        defaultMessage
    )
}
