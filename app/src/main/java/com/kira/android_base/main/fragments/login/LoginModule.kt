package com.kira.android_base.main.fragments.login

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    single { LoginRepository() }
    scope<LoginFragment> { viewModel { LoginViewModel(get()) } }
}
