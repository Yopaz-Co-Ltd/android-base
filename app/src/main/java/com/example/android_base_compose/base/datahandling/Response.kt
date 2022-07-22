package com.example.android_base_compose.base.datahandling

import kotlinx.coroutines.flow.Flow

data class ErrorResponse(
    var code: Int?, val message: String?
) {
    companion object {
        const val UNKNOWN_ERROR_CODE = 9999
        const val EMPTY_DATA_CODE = 9998
        const val EMPTY_DATA_MESSAGE = "Receive nothing, your api sever"
    }
}

sealed class Response<T>(data: T?, error: ErrorResponse?) {
    class Loading : Response<Nothing>(null, null)
    data class Success<T>(val data: T?) : Response<T>(data, null)
    data class Error(val error: ErrorResponse?) : Response<Nothing>(null, error)
}

suspend fun <T> Flow<Response<out T>>.handleFlowResponse(
    onLoading: () -> Unit = {},
    onError: (error: ErrorResponse?) -> Unit = {},
    onSuccess: ((data: T?) -> Unit) = {},
) {
    collect { response ->
        when (response) {
            is Response.Loading -> onLoading()
            is Response.Error -> onError(response.error)
            is Response.Success -> {
                response.data?.let { data ->
                    onSuccess(data)
                    return@collect
                }
                onError(
                    ErrorResponse(
                        ErrorResponse.EMPTY_DATA_CODE,
                        ErrorResponse.EMPTY_DATA_MESSAGE
                    )
                )
            }
        }
    }
}
