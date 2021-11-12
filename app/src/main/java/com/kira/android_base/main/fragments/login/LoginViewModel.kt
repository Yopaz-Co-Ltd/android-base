package com.kira.android_base.main.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kira.android_base.base.ui.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    private val _userNameLiveData = MutableLiveData<String>()
    val userNameLiveData: LiveData<String> = _userNameLiveData

    fun login() {
        viewModelScope.launch {
            _loadingLiveData.postValue(true)
            val result = loginRepository.login()
            result?.data?.let { user ->
                _loadingLiveData.postValue(false)
                user.name?.let { _userNameLiveData.postValue(it) }
                return@launch
            }

            _loadingLiveData.postValue(false)
            result?.error?.let { error ->
                _errorLiveData.postValue(error)
            }
        }
    }
}
