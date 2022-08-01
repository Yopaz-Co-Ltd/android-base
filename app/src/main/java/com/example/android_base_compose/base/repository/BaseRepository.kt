package com.example.android_base_compose.base.repository

import javax.inject.Inject

class BaseRepository @Inject constructor(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
)
