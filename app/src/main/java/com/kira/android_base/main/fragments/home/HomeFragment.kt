package com.kira.android_base.main.fragments.home

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.kira.android_base.R
import com.kira.android_base.base.service.MyForegroundService
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.FragmentHomeBinding
import com.kira.android_base.main.MainViewModel
import com.kira.android_base.main.fragments.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun initViews() {
        (viewDataBinding as? FragmentHomeBinding)?.run {
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

            buttonGetDataBindService.setOnClickListener {
                Toast.makeText(
                    context,
                    mainActivity?.getDataBindService().toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        mainViewModel.getLocalUser()
    }

    override fun handleObservables() {
        super.handleObservables()
        MyForegroundService.isTrackingLiveData.observe(viewLifecycleOwner) {
            (viewDataBinding as? FragmentHomeBinding)?.run {
                buttonStartService.isEnabled = !it
                buttonKillService.isEnabled = it
            }
        }
    }
}
