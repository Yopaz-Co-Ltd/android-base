package com.kira.android_base.base.repository

import com.kira.android_base.base.database.AppDatabase
import com.kira.android_base.base.database.daos.BaseDao
import com.kira.android_base.base.database.entities.User
import com.kira.android_base.base.datahandling.Error
import com.kira.android_base.base.datahandling.toResult
import com.kira.android_base.base.dispatcher.IoDispatcher
import com.kira.android_base.base.sharedpreference.SharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val appDatabase: AppDatabase,
    val sharedPreferences: SharedPreferences,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    private suspend fun <T> runTask(task: () -> T) = withContext(dispatcher) {
        runCatching {
            return@withContext task().toResult()
        }.getOrElse {
            Error(Error.Companion.Code.DEFAULT.value.code, it.localizedMessage)
        }.toResult()
    }

    private suspend fun <T> insert(dao: BaseDao<T>, vararg t: T) = runTask { dao.insert(*t) }

    private suspend fun <T> update(dao: BaseDao<T>, vararg t: T) = runTask { dao.update(*t) }

    private suspend fun <T> delete(dao: BaseDao<T>, vararg t: T) = runTask { dao.delete(*t) }

    fun saveSharedPreferencesData(key: String, value: Any) = sharedPreferences.saveData(key, value)

    inline fun <reified T> getSharedPreferencesData(
        key: String,
        defaultValue: T? = null
    ) = sharedPreferences.getData<T>(key, defaultValue) as T?

    suspend fun insertUser(user: User) = insert(appDatabase.userDao(), user)
}
