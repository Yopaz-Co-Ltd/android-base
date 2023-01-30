package com.kira.android_base.main.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kira.android_base.base.database.entities.User
import com.kira.android_base.base.supports.extensions.SingleLiveData
import com.kira.android_base.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    private val _userLiveData = SingleLiveData<User?>()
    val userLiveData: LiveData<User?> = _userLiveData

    fun login() {
        viewModelScope.launch {
            _loadingLiveData.postValue(true)
            val result = loginRepository.login()
            result?.data?.let { user ->
                _loadingLiveData.postValue(false)
                _userLiveData.postValue(user)
                return@launch
            }

            _loadingLiveData.postValue(false)
            result?.error?.let { error ->
                _errorLiveData.postValue(error)
            }
        }
    }
}
