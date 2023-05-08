package com.kira.android_base.main.fragments.login

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kira.android_base.R
import com.kira.android_base.base.MainCoroutineRule
import com.kira.android_base.base.getOrAwaitValue
import com.kira.android_base.base.repository.auth.FakeAuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    companion object {
        private const val ERROR_INVALID_EMAIL = "Email is invalid!"
        private const val ERROR_PASSWORD_DOES_NOT_HAVE_ENOUGH_CHARACTERS = "Password must have at least 6 characters!"
        private const val ERROR_PASSWORD_DOES_NOT_CONTAIN_ANY_DIGITS = "Password must have at least 1 digit!"
    }

    @Mock
    private lateinit var context: Context

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeAuthRepository: FakeAuthRepository
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        fakeAuthRepository = FakeAuthRepository()
        loginViewModel = LoginViewModel(fakeAuthRepository)
    }

    @Test
    fun `login with email does not match with email format, returns error`() = runTest {
        `when`(context.getString(R.string.error_invalid_email)).thenReturn(ERROR_INVALID_EMAIL)
        loginViewModel.login(context, "kira", "kira1234")
        val error = loginViewModel.errorLiveData.getOrAwaitValue()
        assertThat(error.message).isEqualTo(ERROR_INVALID_EMAIL)
    }

    @Test
    fun `login with password is less than 6 characters, returns error`() {
        `when`(context.getString(R.string.error_password_does_not_have_enough_characters)).thenReturn(
            ERROR_PASSWORD_DOES_NOT_HAVE_ENOUGH_CHARACTERS
        )
        loginViewModel.login(context, "kira@gmail.com", "kira1")
        val error = loginViewModel.errorLiveData.getOrAwaitValue()
        assertThat(error.message).isEqualTo(ERROR_PASSWORD_DOES_NOT_HAVE_ENOUGH_CHARACTERS)
    }

    @Test
    fun `login with password does not contain any digits, returns error`() {
        `when`(context.getString(R.string.error_password_does_not_contain_any_digits)).thenReturn(
            ERROR_PASSWORD_DOES_NOT_CONTAIN_ANY_DIGITS
        )
        loginViewModel.login(context, "kira@gmail.com", "kiratest")
        val error = loginViewModel.errorLiveData.getOrAwaitValue()
        assertThat(error.message).isEqualTo(ERROR_PASSWORD_DOES_NOT_CONTAIN_ANY_DIGITS)
    }

    @Test
    fun `login with valid inputs and they match with server data, returns success`() {
        loginViewModel.login(context, "kira@gmail.com", "kira1234")
        val isLoggedIn = loginViewModel.isLoggedInLiveData.getOrAwaitValue()
        assertThat(isLoggedIn).isEqualTo(true)
    }

    @Test
    fun `login with valid inputs and they does not match with server data, returns error`() {
        fakeAuthRepository.setShouldShowLoginError(true)
        loginViewModel.login(context, "kira@gmail.com", "kira1234")
        val error = loginViewModel.errorLiveData.getOrAwaitValue()
        assertThat(error.message).isEqualTo(FakeAuthRepository.ERROR_LOGIN_FAILED)
    }
}
