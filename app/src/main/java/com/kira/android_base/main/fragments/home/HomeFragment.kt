package com.kira.android_base.main.fragments.home

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.FragmentHomeBinding
import com.kira.android_base.main.MainActivity
import com.kira.android_base.main.MainViewModel
import com.kira.android_base.main.fragments.login.LoginFragment
import com.kira.android_base.main.fragments.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val mainActivity: MainActivity? by lazy { activity as? MainActivity }
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun initViews() {
        binding.run {
            mainViewModel = this@HomeFragment.mainViewModel
            lifecycleOwner = viewLifecycleOwner

            brvHome.apply {
                setAdapter(HomeRecyclerViewAdapter().apply {
                    list.addAll((1..10).map { "$it" })
                })
                setOnRefreshListener {
                    Log.d(LoginFragment.TAG, "initViews: setOnRefreshListener")
                }
            }
            buttonSetting.setOnClickListener {
                mainActivity?.openFragment(SettingFragment())
            }
        }
        mainViewModel.getLocalUser()
    }
}
