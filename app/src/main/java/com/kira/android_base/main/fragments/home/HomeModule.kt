package com.kira.android_base.main.fragments.home

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    scope<HomeFragment> { viewModel{HomeViewModel(get())}}
}
