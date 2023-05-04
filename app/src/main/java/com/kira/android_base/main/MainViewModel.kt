package com.kira.android_base.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kira.android_base.base.database.entities.User
import com.kira.android_base.base.repository.auth.AuthRepository
import com.kira.android_base.base.repository.user.UserRepository
import com.kira.android_base.base.ui.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _logoutSuccessfullyLiveData = MutableLiveData<Unit>()
    val logoutSuccessfullyLiveData: LiveData<Unit> = _logoutSuccessfullyLiveData

    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> = _userLiveData

    fun getLocalAccessToken() = authRepository.getLocalAccessToken()

    fun getLocalUser() {
        viewModelScope.launch {
            val result = userRepository.getLocalUser()
            result.data?.let {
                _userLiveData.postValue(it)
                return@launch
            }

            result.error?.let { error ->
                _errorLiveData.postValue(error)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            val result = authRepository.logout()
            result.data?.let {
                _logoutSuccessfullyLiveData.postValue(Unit)
                return@launch
            }

            result.error?.let { error ->
                _errorLiveData.postValue(error)
            }
        }
    }
}
