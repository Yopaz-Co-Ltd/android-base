package com.kira.android_base.base

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

data class Result<T>(
    val data: T?,
    val error: Error?
)

fun <T> T.toResult(): Result<T> {
    return Result(data = this, error = null)
}

data class Error(
    var code: Int?,
    val message: String?
) {
    companion object {
        enum class Code(val value: Int) {
            DEFAULT(1000),
            NETWORK(1001),
            UNAUTHORIZED(401)
        }
    }

    fun <T> toResult(): Result<T> {
        return Result(data = null, error = this)
    }
}

fun Throwable.toError(): Error {
    return when (this) {
        is SocketTimeoutException, is UnknownHostException, is ConnectException -> Error(
            Error.Companion.Code.NETWORK.value,
            localizedMessage ?: toString()
        )
        else -> Error(
            Error.Companion.Code.DEFAULT.value,
            localizedMessage ?: toString()
        )
    }
}
