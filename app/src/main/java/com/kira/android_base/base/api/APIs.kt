package com.kira.android_base.base.api

import com.kira.android_base.base.api.models.response.LoginResponse
import io.reactivex.Observable
import retrofit2.http.POST

interface APIs {

    @POST("login")
    fun login(): Observable<LoginResponse>
}
