package com.kira.android_base.base.repository

import android.util.Log
import com.kira.android_base.base.database.AppDatabase
import com.kira.android_base.base.database.daos.BaseDao
import com.kira.android_base.base.database.entities.User
import com.kira.android_base.base.datahandling.Result
import com.kira.android_base.base.reactivex.AppReactivexSchedulers
import com.kira.android_base.base.supports.extensions.TAG
import io.reactivex.Observable

class LocalDataSource(
    private val appDatabase: AppDatabase,
    private val appReactivexSchedulers: AppReactivexSchedulers
) {

    private fun <T> runTask(handle: () -> T): Observable<Result<T>> {
        return Observable.create<Result<T>> { emitter ->
            try {
                emitter.onNext(Result(handle.invoke(), null))
                emitter.onComplete()
            } catch (throwable: Throwable) {
                Log.d(TAG, "runTask: $throwable")
                emitter.onError(throwable)
            }
        }.subscribeOn(appReactivexSchedulers.io())
            .observeOn(appReactivexSchedulers.androidMainThread())
    }

    private fun <T> insert(dao: BaseDao<T>, vararg t: T): Observable<Result<List<Long>>> =
        runTask { dao.insert(*t) }

    private fun <T> update(dao: BaseDao<T>, vararg t: T): Observable<Result<Int>> =
        runTask { dao.update(*t) }

    private fun <T> delete(dao: BaseDao<T>, vararg t: T): Observable<Result<Int>> =
        runTask { dao.delete(*t) }

    fun insertUser(user: User) = insert(appDatabase.userDao(), user)
}
