package com.kira.android_base.base.api

import android.content.Context
import com.kira.android_base.BuildConfig
import com.kira.android_base.R
import com.kira.android_base.base.database.AppDatabase
import com.readystatesoftware.chuck.api.ChuckCollector
import com.readystatesoftware.chuck.api.ChuckInterceptor
import com.readystatesoftware.chuck.api.RetentionManager
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val CACHE_SIZE = 10L * 1024L * 1024L
const val TIME_OUT = 60L
const val CHUCK_MAX_CONTENT_LENGTH = 250000L

val APIsModule = module {
    single { provideMoshi() }
    single { provideCallAdapterFactory() }
    single { provideAppInterceptor(androidContext(), get()) }
    single { provideLoggingInterceptor() }
    single { provideCache(androidContext()) }
    single { provideOkHttp(androidContext(), get(), get(), get()) }
    single { provideRetrofit(androidContext(), get(), get(), get()) }
    single { provideAPIs(get()) }
}

fun provideMoshi(): Moshi = Moshi.Builder().build()

fun provideCallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    else
        loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

    return loggingInterceptor
}

fun provideAppInterceptor(context: Context, database: AppDatabase) =
    AppInterceptor(context, database)

fun provideCache(context: Context): Cache = Cache(context.cacheDir, CACHE_SIZE)

fun provideOkHttp(
    context: Context,
    cache: Cache,
    appInterceptor: AppInterceptor,
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    val collector = ChuckCollector(context)
        .showNotification(true)
        .retentionManager(RetentionManager(context, ChuckCollector.Period.FOREVER))

    val chuckInterceptor = ChuckInterceptor(context, collector)
        .maxContentLength(CHUCK_MAX_CONTENT_LENGTH)

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

fun provideRetrofit(
    context: Context,
    okHttpClient: OkHttpClient,
    moshi: Moshi,
    rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
): Retrofit =
    Retrofit.Builder()
        .baseUrl(context.resources.getString(R.string.base_url))
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .build()

fun provideAPIs(retrofit: Retrofit): APIs = retrofit.create(APIs::class.java)
