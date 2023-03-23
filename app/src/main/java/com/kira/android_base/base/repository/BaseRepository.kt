package com.kira.android_base.base.repository

import javax.inject.Inject

open class BaseRepository {

    @Inject
    lateinit var localDataSource: LocalDataSource

    @Inject
    lateinit var remoteDataSource: RemoteDataSource
}
