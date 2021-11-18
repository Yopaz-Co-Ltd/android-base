@file:Suppress("DEPRECATION")

package com.kira.android_base.base.supports.extensions

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import com.kira.android_base.R

/**
 * Before using, need to change theme and night-theme to NoActionBar
 * */
fun AppCompatActivity.enableStatusBarTransparent(
    isLightStatusBar: Boolean = true
) {
    window?.apply {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility =
            if (isLightStatusBar) View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        this.statusBarColor = Color.TRANSPARENT
    }
}

fun AppCompatActivity.disableStatusBarTransparent() {
    window?.apply {
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        val nightModeFlags = context.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        this.statusBarColor = context.getColor(
            when (nightModeFlags) {
                Configuration.UI_MODE_NIGHT_YES -> R.color.purple_200
                else -> R.color.purple_500
            }
        )
    }
}

fun AppCompatActivity.hideStatusBar(root: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val controller = root.windowInsetsController ?: return
        controller.hide(WindowInsetsCompat.Type.statusBars())
        controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    } else {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}

fun AppCompatActivity.showStatusBar(root: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val controller = root.windowInsetsController ?: return
        controller.show(WindowInsetsCompat.Type.statusBars())
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}

fun AppCompatActivity.addOneTimeBackStackListener(onBackStackChange: () -> Unit) {
    supportFragmentManager.apply {
        addOnBackStackChangedListener(object :
            FragmentManager.OnBackStackChangedListener {
            override fun onBackStackChanged() {
                onBackStackChange()
                removeOnBackStackChangedListener(this)
            }
        })
    }
}
