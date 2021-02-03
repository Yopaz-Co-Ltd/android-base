package com.kira.android_base.base.reactivex

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppReactivexSchedulers {

    fun io(): Scheduler = Schedulers.io()

    fun androidMainThread(): Scheduler = AndroidSchedulers.mainThread()
}
