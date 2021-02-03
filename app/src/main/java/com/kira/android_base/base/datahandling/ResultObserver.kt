package com.kira.android_base.base.datahandling

import io.reactivex.observers.DisposableObserver

abstract class ResultObserver<T> : DisposableObserver<Result<T>>() {

    override fun onNext(result: Result<T>) {
        when {
            result.data != null -> onSuccess(result.data)
            result.error != null -> onError(result.error)
        }
    }

    final override fun onError(e: Throwable) {
        onError(convertError(e))
    }

    final override fun onComplete() {}

    abstract fun onSuccess(success: T)

    open fun onError(error: Error) {}

    private fun convertError(error: Throwable): Error {
        return Error(
            Error.Companion.Code.DEFAULT.value.code,
            error.localizedMessage ?: Error.Companion.Code.DEFAULT.value.message
        )
    }
}
