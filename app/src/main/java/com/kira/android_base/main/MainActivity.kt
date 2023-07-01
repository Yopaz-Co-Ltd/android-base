package com.kira.android_base.main

import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kira.android_base.R
import com.kira.android_base.base.supports.utils.Enums
import com.kira.android_base.base.ui.BaseActivity
import com.kira.android_base.databinding.ActivityMainBinding
import com.kira.android_base.main.fragments.home.HomeFragment
import com.kira.android_base.main.fragments.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private val mainViewModel: MainViewModel by viewModels()

    override fun initViews() {
        invalidateAuthState()
    }

    override fun handleObservables() {
        super.handleObservables()

        mainViewModel.logoutSuccessfullyLiveData.observe(this) {
            invalidateAuthState()
        }

        mainViewModel.errorLiveData.observe(this) { error ->
            showErrorDialog(error?.message ?: return@observe)
        }

        onBackPressedDispatcher.addCallback(this) {
            if (supportFragmentManager.backStackEntryCount > 1) {
                supportFragmentManager.popBackStack()
                return@addCallback
            }
            finish()
        }
    }

    fun openFragment(fragment: Fragment, transactionType: Enums.TransactionType? = null) {
        if (supportFragmentManager.isDestroyed) return
        supportFragmentManager.beginTransaction().apply {
            addToBackStack(null)
            when (transactionType) {
                Enums.TransactionType.OPEN_CLOSE -> {
                    setCustomAnimations(
                        R.anim.up_enter, R.anim.no_animation, R.anim.no_animation, R.anim.down_exit
                    )
                }

                else -> {
                    setCustomAnimations(
                        R.anim.right_enter, R.anim.left_exit, R.anim.left_enter, R.anim.right_exit
                    )
                }
            }
            setPrimaryNavigationFragment(fragment)
            add(R.id.frame_layout_container, fragment, fragment::class.java.simpleName)
            if (supportFragmentManager.isStateSaved) commitAllowingStateLoss() else commit()
        }
    }

    fun invalidateAuthState() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        mainViewModel.getLocalAccessToken().takeIf { !it.isNullOrBlank() }?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            openFragment(HomeFragment())
            return
        }
        openFragment(LoginFragment())
    }
}
