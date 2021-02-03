package com.kira.android_base.base.repository

import org.koin.dsl.module

val repositoryModule = module {
    single { LocalDataSource(get(), get()) }
    single { RemoteDataSource(get(), get()) }
}
