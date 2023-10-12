package com.kira.android_base.base.repository

import com.kira.android_base.base.repository.auth.AuthRepository
import com.kira.android_base.base.repository.auth.DefaultAuthRepository
import com.kira.android_base.base.repository.home.DefaultHomeRepository
import com.kira.android_base.base.repository.home.HomeRepository
import com.kira.android_base.base.repository.user.DefaultUserRepository
import com.kira.android_base.base.repository.user.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { DefaultAuthRepository(androidContext(), get(), get(), get()) }
    single<UserRepository> { DefaultUserRepository(get()) }
    single<HomeRepository> { DefaultHomeRepository(androidContext(),get()) }
}
