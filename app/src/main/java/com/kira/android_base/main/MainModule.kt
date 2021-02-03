package com.kira.android_base.main

import com.kira.android_base.main.fragments.login.loginModule
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val mainModule = module {
    single { MainRepository() }
    scope<MainActivity> {
        viewModel { MainViewModel(get()) }
        loadKoinModules(
            listOf(
                loginModule
            )
        )
    }
}
