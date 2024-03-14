package com.kira.android_base.main.fragments.home

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.FragmentHomeBinding
import com.kira.android_base.main.MainViewModel
import com.kira.android_base.main.fragments.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override val viewModel: MainViewModel by activityViewModels()

    override fun initViews() {
        binding.run {
            mainViewModel = this@HomeFragment.viewModel
            lifecycleOwner = viewLifecycleOwner

            brvHome.apply {
                setAdapter(HomeRecyclerViewAdapter().apply {
                    list.addAll((1..10).map { "$it" })
                })
                setOnRefreshListener {
                    Log.d(LoginFragment.TAG, "initViews: setOnRefreshListener")
                }
            }
        }
        viewModel.getLocalUser()
    }
}
