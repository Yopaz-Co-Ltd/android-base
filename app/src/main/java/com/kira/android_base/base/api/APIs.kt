package com.kira.android_base.base.api

import com.kira.android_base.base.api.models.response.BaseResponse
import com.kira.android_base.base.database.entities.User
import retrofit2.Response
import retrofit2.http.POST

interface APIs {

    @POST("login")
    suspend fun login(): Response<BaseResponse<User>>
}
