package com.kira.android_base.main

import com.kira.android_base.base.datahandling.Result
import com.kira.android_base.base.ui.BaseViewModel

class MainViewModel(
    val mainRepository: MainRepository
) : BaseViewModel() {

    fun saveSharedPreferencesData(
        key: String,
        value: Any,
        callback: (Result<Unit>) -> Unit
    ) {
        subscribeCallback(mainRepository.saveSharedPreferencesData(key, value), callback)
    }

    inline fun <reified T> getSharedPreferencesData(
        key: String,
        defaultValue: T? = null,
        noinline callback: (Result<T?>) -> Unit
    ) {
        subscribeCallback(mainRepository.getSharedPreferencesData<T>(key, defaultValue), callback)
    }
}
