package com.kira.android_base.base.database.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.kira.android_base.base.database.AppDatabase
import com.kira.android_base.base.database.TEST_APP_DATABASE_NAME
import com.kira.android_base.base.database.entities.User
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class UserDaoTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named(TEST_APP_DATABASE_NAME)
    lateinit var appDatabase: AppDatabase

    private lateinit var userDao: UserDao
    private lateinit var initialInsertedUser: User

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        userDao = appDatabase.userDao()
        initialInsertedUser = User(id = 1, name = "Kira", age = 18)
        userDao.insert(initialInsertedUser)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun insertUser() = runTest {
        val allUsers = userDao.getAllUser()
        assertThat(allUsers).contains(initialInsertedUser)
    }

    @Test
    fun updateUser() = runTest {
        val userUpdate = User(id = 1, name = "Kira", age = 19)
        userDao.update(userUpdate)
        val allUsers = userDao.getAllUser()
        assertThat(allUsers).contains(userUpdate)
    }

    @Test
    fun deleteUser() = runTest {
        userDao.delete(initialInsertedUser)
        val allUsers = userDao.getAllUser()
        assertThat(allUsers).doesNotContain(initialInsertedUser)
    }

    @Test
    fun getUser() = runTest {
        val gottenUser = userDao.getUser()
        assertThat(initialInsertedUser).isEqualTo(gottenUser)
    }

    @Test
    fun getAllUser() {
        val secondUser = User(id = 2, name = "Carl", age = 19)
        userDao.insert(secondUser)
        val allUsers = userDao.getAllUser()
        assertThat(allUsers).containsExactly(initialInsertedUser, secondUser)
    }

    @Test
    fun deleteAllUser() {
        val secondUser = User(id = 2, name = "Carl", age = 19)
        userDao.insert(secondUser)
        userDao.deleteAllUser()
        val allUsers = userDao.getAllUser()
        assertThat(allUsers).isEmpty()
    }
}
