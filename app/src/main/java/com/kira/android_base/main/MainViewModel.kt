package com.kira.android_base.main

import com.kira.android_base.base.datahandling.Result
import com.kira.android_base.base.datahandling.subscribeCallback
import com.kira.android_base.base.ui.BaseViewModel
import io.reactivex.rxkotlin.plusAssign

class MainViewModel(
    val mainRepository: MainRepository
) : BaseViewModel() {

    fun saveSharedPreferencesData(
        key: String,
        value: Any,
        callback: (Result<Unit>) -> Unit
    ) {
        compositeDisposable += mainRepository.saveSharedPreferencesData(key, value)
            .subscribeCallback(callback)
    }

    inline fun <reified T> getSharedPreferencesData(
        key: String,
        defaultValue: T? = null,
        noinline callback: (Result<T?>) -> Unit
    ) {
        compositeDisposable += mainRepository.getSharedPreferencesData<T>(key, defaultValue)
            .subscribeCallback(callback)
    }
}
