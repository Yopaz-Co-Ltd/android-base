package com.kira.android_base.base.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kira.android_base.base.datahandling.Error

abstract class BaseViewModel : ViewModel() {

    companion object {
        val TAG: String = this::class.java.simpleName
    }

    protected val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    protected val _errorLiveData = MutableLiveData<Error>()
    val errorLiveData: LiveData<Error> = _errorLiveData

    protected val _toastLiveData = MutableLiveData<String>()
    val toastLiveData: LiveData<String> = _toastLiveData
}
