package com.kira.android_base.base.api.auth.models.login

import com.kira.android_base.base.database.entities.User
import com.squareup.moshi.Json

data class LoginResponseModel(
    @Json(name = "access_token")
    val accessToken: String?,
    val user: User?
)
