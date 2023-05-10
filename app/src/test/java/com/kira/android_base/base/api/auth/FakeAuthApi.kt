package com.kira.android_base.base.api.auth

import com.kira.android_base.base.api.BaseResponse
import com.kira.android_base.base.api.auth.models.login.LoginRequestModel
import com.kira.android_base.base.api.auth.models.login.LoginResponseModel
import com.kira.android_base.base.database.entities.User
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeAuthApi : AuthApi {

    private var shouldReturnLoginError = false

    override suspend fun login(loginRequestModel: LoginRequestModel): Response<BaseResponse<LoginResponseModel>> {
        return if (shouldReturnLoginError) {
            Response.error(401, "Unauthorized".toResponseBody())
        } else {
            Response.success(
                BaseResponse(
                    LoginResponseModel("token", User(1, "kira", 19)),
                    "200"
                )
            )
        }
    }

    fun setShouldReturnError(value: Boolean) {
        shouldReturnLoginError = value
    }
}
