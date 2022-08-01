package com.example.android_base_compose.main.ui.login_screen

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_base_compose.R
import com.example.android_base_compose.base.api.entities.LoginRequest
import com.example.android_base_compose.base.datahandling.handleFlowResponse
import com.example.android_base_compose.base.repository.BaseRepository
import com.example.android_base_compose.base.until.KEY_IS_LOGIN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val baseRepository: BaseRepository
) : ViewModel() {

    companion object {
        private const val MIN_PASSWORD_LENGTH = 6
    }

    private val _isShowLoadingState = MutableStateFlow(false)
    val isShowLoadingState = _isShowLoadingState.asStateFlow()

    private val _loginErrorMessageState = MutableSharedFlow<String?>()
    val loginErrorMessageState = _loginErrorMessageState.asSharedFlow()

    val email = MutableStateFlow("")
    var password = MutableStateFlow("")

    fun setEmail(text: String) {
        email.value = text
    }

    fun setPassword(text: String) {
        password.value = text
    }

    suspend fun login(context: Context) {
        val message = when {
            email.value.isBlank() -> {
                context.getString(R.string.login_blank_email)
            }
            password.value.isBlank() -> {
                context.getString(R.string.login_blank_password)
            }
            !Patterns.EMAIL_ADDRESS.matcher(email.value).matches() -> {
                context.getString(R.string.login_invalid_email)
            }
            password.value.length < MIN_PASSWORD_LENGTH -> {
                context.getString(R.string.login_password_too_short)
            }
            else -> {
                null
            }
        }
        message?.let {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            return
        }
        baseRepository.remoteDataSource.login(
            LoginRequest(email.value.trim(), password.value)
        ).handleFlowResponse(onLoading = {
            _isShowLoadingState.value = true
        }, onError = {
            _isShowLoadingState.value = false
            Toast.makeText(context, it?.message, Toast.LENGTH_SHORT).show()
        }) {
            viewModelScope.launch {
                baseRepository.localDataSource.preferencesDataStore.saveData(KEY_IS_LOGIN, true)
            }
            _isShowLoadingState.value = false
        }
    }
}
