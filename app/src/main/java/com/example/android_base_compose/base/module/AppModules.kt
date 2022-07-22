package com.example.android_base_compose.base.module

import android.content.Context
import androidx.room.Room
import androidx.viewbinding.BuildConfig
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.android_base_compose.R
import com.example.android_base_compose.base.api.APIs
import com.example.android_base_compose.base.api.AppInterceptor
import com.example.android_base_compose.base.database.AppDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val APP_DB_NAME = "app_database"
const val CACHE_SIZE = 10L * 1024L * 1024L
const val TIME_OUT = 60L
const val CHUCK_MAX_CONTENT_LENGTH = 250000L

@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, APP_DB_NAME)
                    .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideAppInterceptor(@ApplicationContext context: Context, database: AppDatabase) =
            AppInterceptor(context, database)

    @Singleton
    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache =
            Cache(context.cacheDir, CACHE_SIZE)

    @Singleton
    @Provides
    fun provideOkHttp(
            @ApplicationContext context: Context,
            cache: Cache,
            appInterceptor: AppInterceptor,
            loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val collector =
                ChuckerCollector(context, showNotification = true, RetentionManager.Period.FOREVER)
        val chuckInterceptor = ChuckerInterceptor.Builder(context).collector(collector)
                .maxContentLength(CHUCK_MAX_CONTENT_LENGTH).redactHeaders(emptySet())
                .alwaysReadResponseBody(false).build()

        return OkHttpClient.Builder().connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS).writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .cache(cache).addInterceptor(appInterceptor).addInterceptor(chuckInterceptor)
                .addInterceptor(loggingInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
            @ApplicationContext context: Context, okHttpClient: OkHttpClient, moshi: Moshi
    ): Retrofit = Retrofit.Builder().baseUrl(context.resources.getString(R.string.base_url))
            .client(okHttpClient).addConverterFactory(MoshiConverterFactory.create(moshi)).build()

    @Singleton
    @Provides
    fun provideAPIs(retrofit: Retrofit): APIs = retrofit.create(APIs::class.java)
}
