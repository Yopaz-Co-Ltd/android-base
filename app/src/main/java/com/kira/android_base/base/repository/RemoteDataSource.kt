package com.kira.android_base.base.repository

import com.kira.android_base.base.api.APIs
import com.kira.android_base.base.datahandling.Result
import com.kira.android_base.base.datahandling.retrofitResponseToResult
import com.kira.android_base.base.reactivex.AppReactivexSchedulers
import io.reactivex.Observable

class RemoteDataSource(
    private val apIs: APIs,
    private val appReactivexSchedulers: AppReactivexSchedulers
) {

    private fun <T> Observable<T>.subscribeWithReactivex(): Observable<Result<T>> {
        return retrofitResponseToResult()
            .subscribeOn(appReactivexSchedulers.io())
            .observeOn(appReactivexSchedulers.androidMainThread())
    }

    fun login() = apIs.login().subscribeWithReactivex()
}
