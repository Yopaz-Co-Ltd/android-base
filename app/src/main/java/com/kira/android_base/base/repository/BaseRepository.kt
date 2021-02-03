package com.kira.android_base.base.repository

import org.koin.java.KoinJavaComponent.inject

open class BaseRepository {

    val localDataSource: LocalDataSource by inject(LocalDataSource::class.java)
    val remoteDataSource: RemoteDataSource by inject(RemoteDataSource::class.java)
}
