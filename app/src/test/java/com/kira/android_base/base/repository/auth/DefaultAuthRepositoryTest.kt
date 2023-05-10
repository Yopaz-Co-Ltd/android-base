package com.kira.android_base.base.repository.auth

import android.content.Context
import com.google.common.truth.Truth.assertThat
import com.kira.android_base.R
import com.kira.android_base.base.api.auth.FakeAuthApi
import com.kira.android_base.base.database.daos.FakeUserDao
import com.kira.android_base.base.sharedpreferences.FakeSharedPreferences
import com.kira.android_base.base.supports.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
@ExperimentalCoroutinesApi
class DefaultAuthRepositoryTest {

    @Mock
    private lateinit var context: Context

    private lateinit var fakeSharedPreferences: FakeSharedPreferences
    private lateinit var defaultAuthRepository: DefaultAuthRepository
    private lateinit var fakeAuthApi: FakeAuthApi
    private lateinit var fakeUserDao: FakeUserDao

    @Before
    fun setUp() {
        fakeSharedPreferences = FakeSharedPreferences()
        fakeAuthApi = FakeAuthApi()
        fakeUserDao = FakeUserDao()
        defaultAuthRepository = DefaultAuthRepository(context, fakeSharedPreferences, fakeAuthApi, fakeUserDao)
        `when`(context.getString(R.string.error_login_failed)).thenReturn(FakeAuthRepository.ERROR_LOGIN_FAILED)
    }

    @Test
    fun `get local access token failed, returns default string`() {
        val accessToken = defaultAuthRepository.getLocalAccessToken()
        assertThat(accessToken).isEqualTo(Constants.DEFAULT_STRING)
    }

    @Test
    fun `get local access token successfully, return saved access token`() {
        val fakeAccessToken = "uij123lkajsd"
        fakeSharedPreferences.putString(Constants.ACCESS_TOKEN_KEY, fakeAccessToken)
        val accessToken = defaultAuthRepository.getLocalAccessToken()
        assertThat(accessToken).isEqualTo(fakeAccessToken)
    }

    @Test
    fun `login successfully, returns true`() = runTest {
        val result = defaultAuthRepository.login("kira@gmail.com", "kira1234")
        assertThat(result.data).isEqualTo(true)
    }

    @Test
    fun `login failed, return error`() = runTest {
        fakeAuthApi.setShouldReturnError(true)
        val result = defaultAuthRepository.login("kira@gmail.com", "kira1234")
        assertThat(result.error?.message).isEqualTo(context.getString(R.string.error_login_failed))
    }

    @Test
    fun `logout successfully, returns 1`() = runTest {
        defaultAuthRepository.login("kira@gmail.com", "kira1234")
        val result = defaultAuthRepository.logout()
        assertThat(result.data).isEqualTo(1)
    }

    @Test
    fun `logout failed, returns error`() = runTest {
        defaultAuthRepository.login("kira@gmail.com", "kira1234")
        fakeUserDao.setShouldReturnDeleteAllError(true)
        val result = defaultAuthRepository.logout()
        assertThat(result.error?.message).isEqualTo(FakeUserDao.ERROR_DELETE_ALL_USER_FAILED)
    }
}
