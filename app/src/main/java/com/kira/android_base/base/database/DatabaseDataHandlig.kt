package com.kira.android_base.base.database

import com.kira.android_base.base.Error
import com.kira.android_base.base.database.daos.BaseDao
import com.kira.android_base.base.toResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> runDatabaseTask(task: () -> T) = withContext(Dispatchers.IO) {
    runCatching {
        return@withContext task().toResult()
    }.getOrElse {
        Error(Error.Companion.Code.DEFAULT.value, it.localizedMessage)
    }.toResult()
}

suspend fun <T> insertDatabase(dao: BaseDao<T>, vararg t: T) = runDatabaseTask { dao.insert(*t) }

suspend fun <T> updateDataBase(dao: BaseDao<T>, vararg t: T) = runDatabaseTask { dao.update(*t) }

suspend fun <T> deleteDatabase(dao: BaseDao<T>, vararg t: T) = runDatabaseTask { dao.delete(*t) }
