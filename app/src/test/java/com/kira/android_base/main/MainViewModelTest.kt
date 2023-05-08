package com.kira.android_base.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kira.android_base.base.MainCoroutineRule
import com.kira.android_base.base.getOrAwaitValue
import com.kira.android_base.base.repository.auth.FakeAuthRepository
import com.kira.android_base.base.repository.user.FakeUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var fakeAuthRepository: FakeAuthRepository
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        fakeAuthRepository = FakeAuthRepository()
        mainViewModel = MainViewModel(fakeAuthRepository, fakeUserRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `get local user successfully`() {
        mainViewModel.getLocalUser()
        val user = mainViewModel.userLiveData.getOrAwaitValue()
        assertThat(user).isNotNull()
    }

    @Test
    fun `get local user failed`() {
        fakeUserRepository.setShouldShowGetUserError(true)
        mainViewModel.getLocalUser()
        val error = mainViewModel.errorLiveData.getOrAwaitValue()
        assertThat(error).isNotNull()
    }

    @Test
    fun `logout successfully`() {
        mainViewModel.logout()
        val logoutSuccessfully = mainViewModel.logoutSuccessfullyLiveData.getOrAwaitValue()
        assertThat(logoutSuccessfully).isNotNull()
    }

    @Test
    fun `logout failed`() {
        fakeAuthRepository.setShouldShowLogoutError(true)
        mainViewModel.logout()
        val error = mainViewModel.errorLiveData.getOrAwaitValue()
        assertThat(error.message).isEqualTo(FakeAuthRepository.ERROR_LOGOUT_FAILED)
    }
}
