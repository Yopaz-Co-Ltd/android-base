package com.kira.android_base.base.datahandling

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
        enum class Code(val value: Error) {
            DEFAULT(Error(1000, "Default Error")),
            NETWORK(Error(1001, "Network Error"))
        }
    }

    fun <T> toResult(): Result<T> {
        return Result(data = null, error = this)
    }
}

