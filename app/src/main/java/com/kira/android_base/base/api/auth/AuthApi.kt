package com.kira.android_base.base.api.auth

import com.kira.android_base.base.api.auth.models.login.LoginRequestModel
import com.kira.android_base.base.api.auth.models.login.LoginResponseModel
import com.kira.android_base.base.api.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun login(@Body loginRequestModel: LoginRequestModel): Response<BaseResponse<LoginResponseModel>>
}
