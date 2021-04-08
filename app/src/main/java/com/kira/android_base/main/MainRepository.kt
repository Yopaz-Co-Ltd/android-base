package com.kira.android_base.main

import com.kira.android_base.base.repository.BaseRepository

class MainRepository : BaseRepository() {

    fun saveSharedPreferencesData(key: String, value: Any) =
        localDataSource.saveSharedPreferencesData(key, value)

    inline fun <reified T> getSharedPreferencesData(key: String, defaultValue: T? = null) =
        localDataSource.getSharedPreferencesData<T>(key, defaultValue)
}
