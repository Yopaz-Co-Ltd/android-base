package com.kira.android_base.main

import androidx.fragment.app.Fragment
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseActivity
import com.kira.android_base.main.fragments.login.LoginFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class MainActivity : BaseActivity(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModel()

    override fun initViews() {
        manageActionBarFollowFragment()

        openScreen(LoginFragment.TAG)
    }

    override fun generateNewFragmentWithTag(fragmentTag: String): Fragment? {
        return when (fragmentTag) {
            LoginFragment.TAG -> LoginFragment()
            else -> null
        }
    }

    private fun manageActionBarFollowFragment() {
        supportFragmentManager.addOnBackStackChangedListener {
            when (supportFragmentManager.primaryNavigationFragment) {
                is LoginFragment -> {
                    supportActionBar?.hide()
                }
                else -> {
                    supportActionBar?.show()
                }
            }
        }
    }
}
