package com.kira.android_base.main

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.ActivityMainBinding
import com.kira.android_base.main.fragments.login.LoginFragment

class MainActivity : AppCompatActivity() {

    private var activityMainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val isConsumed = super.dispatchTouchEvent(ev)
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            currentFocus?.let { it ->
                if (it !is EditText) return@let
                val outRect = Rect()
                it.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    it.clearFocus()
                }
            }
        }
        return isConsumed
    }

    private fun initViews() {
        activityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        manageActionBarFollowFragment()

        openScreen(LoginFragment.TAG)
    }

    fun openScreen(fragmentTag: String, fragment: BaseFragment? = null) {
        val transaction = supportFragmentManager.beginTransaction()
            .apply {
                supportFragmentManager.primaryNavigationFragment?.let { currentFragment ->
                    val currentFragmentTag = currentFragment::class.java.simpleName
                    if (fragmentTag == currentFragmentTag) return
                    hide(currentFragment)
                }
                fragment?.let {
                    add(R.id.container, it, it::class.java.simpleName)
                        .addToBackStack(null)
                        .commit()
                    setPrimaryNavigationFragment(it)
                        .addToBackStack(null)
                        .commit()
                    return
                }
            }

        var destinationFragment = supportFragmentManager.findFragmentByTag(fragmentTag)
        if (destinationFragment != null) {
            transaction.show(destinationFragment)
        } else {
            destinationFragment = generateNewFragmentWithTag(fragmentTag) ?: return
            transaction.add(
                R.id.container,
                destinationFragment,
                destinationFragment::class.java.simpleName
            )
        }

        transaction.setPrimaryNavigationFragment(destinationFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun generateNewFragmentWithTag(fragmentTag: String): Fragment? {
        return when (fragmentTag) {
            LoginFragment.TAG -> {
                LoginFragment()
            }
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
