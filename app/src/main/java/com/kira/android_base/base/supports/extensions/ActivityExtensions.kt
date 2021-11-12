@file:Suppress("DEPRECATION")

package com.kira.android_base.base.supports.extensions

import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentManager

fun AppCompatActivity.makeStatusBarTransparent(
    isLightStatusBar: Boolean = true,
    statusBarColor: Int = Color.TRANSPARENT
) {
    window?.apply {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility =
            if (isLightStatusBar) View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        this.statusBarColor = statusBarColor
    }
}

fun AppCompatActivity.marginTopAfterFullScreen(container: View, view: View?) {
    ViewCompat.setOnApplyWindowInsetsListener(
        container
    ) { _, insets ->
        view?.apply {
            setPadding(
                paddingLeft,
                paddingTop + insets.systemWindowInsetTop,
                paddingRight,
                paddingBottom
            )
        }
        insets.consumeSystemWindowInsets()
    }
}

fun AppCompatActivity.hideStatusBar(root: View, isFitsSystemWindows: Boolean = true) {
    WindowCompat.setDecorFitsSystemWindows(window, isFitsSystemWindows)
    WindowInsetsControllerCompat(window, root).let { controller ->
        controller.hide(WindowInsetsCompat.Type.statusBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun AppCompatActivity.showStatusBar(root: View, isFitsSystemWindows: Boolean = false) {
    WindowCompat.setDecorFitsSystemWindows(window, isFitsSystemWindows)
    WindowInsetsControllerCompat(window, root).show(WindowInsetsCompat.Type.statusBars())
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