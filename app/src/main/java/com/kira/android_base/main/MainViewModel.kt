package com.kira.android_base.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kira.android_base.base.database.entities.User
import com.kira.android_base.base.repository.auth.AuthRepository
import com.kira.android_base.base.repository.user.UserRepository
import com.kira.android_base.base.supports.utils.Enums
import com.kira.android_base.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _logoutSuccessfullyLiveData = MutableLiveData<Unit>()
    val logoutSuccessfullyLiveData: LiveData<Unit> = _logoutSuccessfullyLiveData

    private val _userLiveData = MutableLiveData<User>()
    val userLiveData: LiveData<User> = _userLiveData

    private val _commandServiceLiveData = MutableLiveData<Enums.CommandService>()
    val commandServiceLiveData: LiveData<Enums.CommandService> = _commandServiceLiveData

    fun getLocalAccessToken() = authRepository.getLocalAccessToken()

    fun getLocalUser() {
        viewModelScope.launch {
            val result = userRepository.getLocalUser()
            result.data?.let { user ->
                _userLiveData.postValue(user)
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

    fun sendCommandToService(commandService: Enums.CommandService) {
        _commandServiceLiveData.postValue(commandService)
    }
}
