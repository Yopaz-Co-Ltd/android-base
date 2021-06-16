package com.kira.android_base.base.api

import com.kira.android_base.base.api.models.response.BaseResponse
import com.kira.android_base.base.database.entities.User
import io.reactivex.Observable
import retrofit2.http.POST

interface APIs {

    @POST("login")
    fun login(): Observable<BaseResponse<User>>
}
