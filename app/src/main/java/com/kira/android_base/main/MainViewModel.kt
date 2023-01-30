package com.kira.android_base.main

import com.kira.android_base.base.ui.BaseViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val mainRepository: MainRepository
) : BaseViewModel() {

    fun saveSharedPreferencesData(
        key: String,
        value: Any,
    ) = mainRepository.saveSharedPreferencesData(key, value)

    inline fun <reified T> getSharedPreferencesData(
        key: String,
        defaultValue: T? = null,
    ) = mainRepository.getSharedPreferencesData<T>(key, defaultValue)
}
