package com.kira.android_base.main

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseActivity
import com.kira.android_base.main.fragments.login.LoginFragment

class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun initViews() {
        setUpActionBar()
        manageActionBarFollowFragment()
    }

    override fun generateNewFragmentWithTag(fragmentTag: String): Fragment? {
        return when (fragmentTag) {
            LoginFragment.TAG -> LoginFragment()
            else -> null
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp() || super.onSupportNavigateUp()
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

    private fun setUpActionBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }
}
