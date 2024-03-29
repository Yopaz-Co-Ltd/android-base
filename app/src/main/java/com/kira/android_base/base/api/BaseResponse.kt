package com.kira.android_base.base.api

import com.kira.android_base.base.Result
import com.kira.android_base.base.toResult
import com.squareup.moshi.Json

open class BaseResponse<T> {
    @Json(name = "data")
    var data: T? = null

    @Json(name = "status")
    var status: String? = null

    //another fields
}

fun <T> Result<BaseResponse<T>>.extract(): Result<T>? {
    return this.let { result ->
        result.data?.data?.also { return@let it.toResult() }
        return@let result.error?.toResult()
    }
}
