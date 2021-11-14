package com.kira.android_base.base.supports.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.kira.android_base.R

fun NavController.navigateTo(directions: NavDirections) {
    val navOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.enter_from_right)
        .setExitAnim(R.anim.exit_to_left)
        .setPopEnterAnim(R.anim.enter_from_left)
        .setPopExitAnim(R.anim.exit_to_right)
        .build()
    navigate(directions, navOptions)
}
