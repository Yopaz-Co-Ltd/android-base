package com.kira.android_base.base.datahandling

import com.squareup.moshi.Json

data class Error(
    var code: Int,
    @field:Json(name = "message")
    val message: String
) {
    companion object {
        enum class Code(val value: Error) {
            DEFAULT(Error(1000, "Default Error")),
            NETWORK(Error(1001, "Network Error"))
        }
    }
}
