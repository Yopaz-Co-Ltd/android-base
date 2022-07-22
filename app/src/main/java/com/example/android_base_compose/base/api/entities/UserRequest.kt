package com.example.android_base_compose.base.api.entities

import com.squareup.moshi.Json

data class LoginRequest(
    val email: String, val password: String
)

data class LoginResponse(
    @field:Json(name = "message")
    val message: String?
)
