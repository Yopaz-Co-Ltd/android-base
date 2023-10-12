package com.kira.android_base.base.api.home

import com.kira.android_base.base.api.BaseResponse
import com.kira.android_base.base.api.auth.models.login.LoginRequestModel
import com.kira.android_base.base.api.home.models.home.HomeResponseModel
import com.kira.android_base.main.fragments.home.PostItemHomeModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface HomeApi {
    @GET("post/list")
    suspend fun getListPostHome(): Response<BaseResponse<List<PostItemHomeModel>>>

}
