package com.kira.android_base.main

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val mainModule = module {
    single { MainRepository() }
    viewModel { MainViewModel(get()) }
    scope<MainActivity> {
        loadKoinModules(
            fragmentModules
        )
    }
}
