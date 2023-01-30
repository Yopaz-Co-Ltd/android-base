package com.kira.android_base.main.fragments.home

import androidx.navigation.fragment.navArgs
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val homeFragmentArgs by navArgs<HomeFragmentArgs>()

    override fun initViews() {
        (viewDataBinding as FragmentHomeBinding?)?.apply {
            this.args = this@HomeFragment.homeFragmentArgs.args
        }
    }
}
