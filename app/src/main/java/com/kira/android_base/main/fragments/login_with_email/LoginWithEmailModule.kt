package com.kira.android_base.main.fragments.login_with_email

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginWithEmailModule = module {
    scope<LoginWithEmailFragment> { viewModel { LoginWithEmailViewModel(get()) } }
}
