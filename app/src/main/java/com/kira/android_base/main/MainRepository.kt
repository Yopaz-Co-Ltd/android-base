package com.kira.android_base.main

import com.kira.android_base.base.repository.LocalDataSource
import javax.inject.Inject

class MainRepository @Inject constructor(
    val localDataSource: LocalDataSource
) {

    fun saveSharedPreferencesData(key: String, value: Any) =
        localDataSource.saveSharedPreferencesData(key, value)

    inline fun <reified T> getSharedPreferencesData(key: String, defaultValue: T? = null) =
        localDataSource.getSharedPreferencesData<T>(key, defaultValue)
}
