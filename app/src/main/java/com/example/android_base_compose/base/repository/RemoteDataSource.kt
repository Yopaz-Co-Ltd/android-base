package com.example.android_base_compose.base.repository

import com.example.android_base_compose.base.api.APIs
import com.example.android_base_compose.base.api.entities.LoginRequest
import com.example.android_base_compose.base.datahandling.ErrorResponse
import com.example.android_base_compose.base.datahandling.Response
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apIs: APIs,
) {

    private fun <T> getResponseFlow(function: suspend () -> T) = flow {
        emit(Response.Loading())
        try {
            val response = function.invoke()
            emit(Response.Success(response))
        } catch (exception: Exception) {
            if (exception is HttpException) {
                emit(Response.Error(ErrorResponse(exception.code(), exception.message)))
                return@flow
            }
            emit(Response.Error(ErrorResponse(ErrorResponse.UNKNOWN_ERROR_CODE, exception.message)))
        }

    }

    suspend fun login(loginRequest: LoginRequest) = getResponseFlow { apIs.login(loginRequest) }

    suspend fun fetchUsers() = getResponseFlow { apIs.fetchUsers() }
}
