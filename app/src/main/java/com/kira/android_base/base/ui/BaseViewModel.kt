package com.kira.android_base.base.ui

import androidx.lifecycle.ViewModel
import com.kira.android_base.base.datahandling.Result
import com.kira.android_base.base.datahandling.subscribeCallback
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import org.koin.core.component.KoinComponent

abstract class BaseViewModel : ViewModel(), KoinComponent {

    companion object {
        val TAG: String = this::class.java.simpleName
    }

    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun <T> subscribeCallback(observable: Observable<Result<T>>, callback: (Result<T>) -> Unit) {
        compositeDisposable += observable.subscribeCallback(callback)
    }
}
