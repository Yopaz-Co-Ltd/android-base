package com.example.android_base_compose.base.api

import com.example.android_base_compose.base.api.entities.LoginRequest
import com.example.android_base_compose.base.api.entities.LoginResponse
import com.example.android_base_compose.base.api.entities.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIs {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("card")
    suspend fun fetchUsers(): List<UserResponse>
}
