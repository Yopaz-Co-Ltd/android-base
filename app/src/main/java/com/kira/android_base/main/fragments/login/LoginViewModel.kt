package com.kira.android_base.main.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kira.android_base.base.repository.auth.AuthRepository
import com.kira.android_base.base.ui.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
) : BaseViewModel() {

    private val _isLoggedInLiveData = MutableLiveData<Boolean>()
    val isLoggedInLiveData: LiveData<Boolean> = _isLoggedInLiveData

    fun login() {
        viewModelScope.launch {
            _loadingLiveData.postValue(true)
            // todo remove fake data
            val result = authRepository.login("email", "password")
            _loadingLiveData.postValue(false)
            result.data?.let { isLoggedIn ->
                _isLoggedInLiveData.postValue(isLoggedIn)
                return@launch
            }

            result.error?.let { error ->
                _errorLiveData.postValue(error)
            }
        }
    }
}
