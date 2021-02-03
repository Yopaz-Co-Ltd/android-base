package com.kira.android_base.base.api

import android.content.Context
import com.kira.android_base.base.database.AppDatabase
import okhttp3.Interceptor
import okhttp3.Response

class AppInterceptor(context: Context, database: AppDatabase) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(request)
    }
}
