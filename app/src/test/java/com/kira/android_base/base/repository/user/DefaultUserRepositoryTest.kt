package com.kira.android_base.base.repository.user

import com.kira.android_base.base.database.daos.FakeUserDao
import com.kira.android_base.base.database.entities.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultUserRepositoryTest {

    private lateinit var defaultUserRepository: DefaultUserRepository
    private lateinit var fakeUserDao: FakeUserDao

    @Before
    fun setUp() {
        fakeUserDao = FakeUserDao()
        defaultUserRepository = DefaultUserRepository(fakeUserDao)
    }

    @Test
    fun `get local user successfully, returns user`() = runTest {
        val user = User(1, "kira", 19)
        fakeUserDao.insert(user)
        val gottenUserResult = defaultUserRepository.getLocalUser()
        assert(gottenUserResult.data == user)
    }

    @Test
    fun `get local user failed, returns null`() = runTest {
        val gottenUserResult = defaultUserRepository.getLocalUser()
        assert(gottenUserResult.data == null)
    }
}
