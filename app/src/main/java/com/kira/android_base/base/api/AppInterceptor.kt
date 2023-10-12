package com.kira.android_base.base.api

import android.content.SharedPreferences
import com.kira.android_base.base.supports.utils.ACCESS_TOKEN_KEY
import com.kira.android_base.base.supports.utils.DEFAULT_STRING
import okhttp3.Interceptor
import okhttp3.Response

class AppInterceptor(sharedPreferences: SharedPreferences) : Interceptor {
    private val token = sharedPreferences.getString(ACCESS_TOKEN_KEY, DEFAULT_STRING)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestWithToken = request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(requestWithToken)
    }
}
