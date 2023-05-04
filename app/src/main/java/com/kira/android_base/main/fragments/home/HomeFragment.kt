package com.kira.android_base.main.fragments.home

import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.FragmentHomeBinding
import com.kira.android_base.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun initViews() {
        (viewDataBinding as? FragmentHomeBinding)?.run {
            mainViewModel = this@HomeFragment.mainViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        mainViewModel.getLocalUser()
    }
}
