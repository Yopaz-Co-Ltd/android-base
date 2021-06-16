package com.kira.android_base.base.api.models.response

import com.kira.android_base.base.datahandling.Result
import com.kira.android_base.base.datahandling.asResult
import com.squareup.moshi.Json

open class BaseResponse<T> {
    @Json(name = "status")
    var status: String? = null

    @Json(name = "data")
    var data: T? = null
}

fun <T> Result<BaseResponse<T>>.extract(): Result<T>? {
    return this.let {
        it.data?.data?.also { return@let it.asResult() }
        return@let it.error?.asResult()
    }
}
