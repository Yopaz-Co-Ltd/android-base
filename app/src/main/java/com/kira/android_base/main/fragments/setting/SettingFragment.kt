package com.kira.android_base.main.fragments.setting

import androidx.fragment.app.activityViewModels
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.FragmentSettingBinding
import com.kira.android_base.main.MainViewModel

class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun initViews() {
        binding.mainViewModel = mainViewModel
    }
}
