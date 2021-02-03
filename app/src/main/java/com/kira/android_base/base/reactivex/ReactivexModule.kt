package com.kira.android_base.base.reactivex

import org.koin.dsl.module

val reactivexModule = module {
    single { AppReactivexSchedulers() }
}
