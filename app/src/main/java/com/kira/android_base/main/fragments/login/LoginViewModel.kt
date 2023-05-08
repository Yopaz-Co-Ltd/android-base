package com.kira.android_base.main.fragments.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kira.android_base.R
import com.kira.android_base.base.Error
import com.kira.android_base.base.repository.auth.AuthRepository
import com.kira.android_base.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel() {

    companion object {
        const val EMAIL_REGEX = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
        const val MIN_PASSWORD_LENGTH = 6
    }

    private val _isLoggedInLiveData = MutableLiveData<Boolean>()
    val isLoggedInLiveData: LiveData<Boolean> = _isLoggedInLiveData

    /*
    * The input email is invalid if ...
    * ... the email does not match email format
    * The input password is invalid if ...
    * ... the password is less than 6 characters
    * ... the password does not contain any digits
    * */
    fun login(context: Context, email: String, password: String) {
        if (!Pattern.compile(EMAIL_REGEX).matcher(email).matches()) {
            _errorLiveData.postValue(
                Error(
                    Error.Companion.Code.DEFAULT.value,
                    context.getString(R.string.error_invalid_email)
                )
            )
            return
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            _errorLiveData.postValue(
                Error(
                    Error.Companion.Code.DEFAULT.value,
                    context.getString(R.string.error_password_does_not_have_enough_characters)
                )
            )
            return
        }
        if (password.count { it.isDigit() } == 0) {
            _errorLiveData.postValue(
                Error(
                    Error.Companion.Code.DEFAULT.value,
                    context.getString(R.string.error_password_does_not_contain_any_digits)
                )
            )
            return
        }
        viewModelScope.launch {
            _loadingLiveData.postValue(true)
            val result = authRepository.login(email, password)
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
