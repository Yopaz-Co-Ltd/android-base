package com.kira.mythnovel.main

import com.kira.mythnovel.base.datahandling.Result
import com.kira.mythnovel.base.datahandling.ResultObserverWithFullCallback
import com.kira.mythnovel.base.ui.BaseViewModel
import io.reactivex.rxkotlin.plusAssign

class MainViewModel(
    val mainRepository: MainRepository
) : BaseViewModel() {

    fun saveSharedPreferencesData(key: String, value: Any, callback: (Result<Unit>) -> Unit) {
        compositeDisposable += mainRepository.saveSharedPreferencesData(key, value)
            .subscribeWith(ResultObserverWithFullCallback(callback))
    }

    inline fun <reified T> getSharedPreferencesData(
        key: String,
        defaultValue: T? = null,
        noinline callback: (Result<T?>) -> Unit
    ) {
        compositeDisposable += mainRepository.getSharedPreferencesData<T>(key, defaultValue)
            .subscribeWith(ResultObserverWithFullCallback(callback))
    }
}
