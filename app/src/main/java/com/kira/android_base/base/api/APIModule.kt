package com.kira.android_base.base.api

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.kira.android_base.BuildConfig
import com.kira.android_base.base.api.auth.AuthApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val CACHE_SIZE = 10L * 1024L * 1024L
const val TIME_OUT = 60L
const val CHUCK_MAX_CONTENT_LENGTH = 250000L

val APIsModule = module {
    single { provideMoshi() }
    single { provideAppInterceptor() }
    single { provideLoggingInterceptor() }
    single { provideCache(androidContext()) }
    single { provideOkHttp(androidContext(), get(), get(), get()) }
    single { provideRetrofit(get(), get()) }
    single { provideAuthApi(get()) }
}

fun provideMoshi(): Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    else
        loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

    return loggingInterceptor
}

private fun provideAppInterceptor() = AppInterceptor()

private fun provideCache(context: Context): Cache = Cache(context.cacheDir, CACHE_SIZE)

private fun provideOkHttp(
    context: Context,
    cache: Cache,
    appInterceptor: AppInterceptor,
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    val collector =
        ChuckerCollector(context, showNotification = true, RetentionManager.Period.FOREVER)
    val chuckInterceptor = ChuckerInterceptor.Builder(context)
        .collector(collector)
        .maxContentLength(CHUCK_MAX_CONTENT_LENGTH)
        .redactHeaders(emptySet())
        .alwaysReadResponseBody(false)
        .build()

    return OkHttpClient.Builder()
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .cache(cache)
        .addInterceptor(appInterceptor)
        .addInterceptor(chuckInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

private fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)
