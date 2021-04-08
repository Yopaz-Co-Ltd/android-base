package com.kira.android_base.base.supports.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

private var VIEW_ANIMATION_DURATION = 300L

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.show(isShow: Boolean) {
    if (isShow) visible() else gone()
}

fun View.setHeight(newHeight: Int) {
    (layoutParams as ViewGroup.LayoutParams).apply { height = newHeight }
}

fun View.setWidth(newWidth: Int) {
    (layoutParams as ViewGroup.LayoutParams).apply { width = newWidth }
}

fun View.setMargin(
    left: Int = marginLeft,
    top: Int = marginTop,
    right: Int = marginRight,
    bottom: Int = marginBottom
) {
    val marginLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    marginLayoutParams.setMargins(left, top, right, bottom)
    this.layoutParams = marginLayoutParams
}

fun View.setOnlyPadding(
    left: Int = paddingStart,
    top: Int = paddingTop,
    right: Int = paddingEnd,
    bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}

fun View.goneWithTranslationAnimation(
    isTopToBottom: Boolean = true,
    duration: Long = VIEW_ANIMATION_DURATION,
    translationRatio: Float = 1f,
    onAnimationEnd: (() -> Unit)? = null
) {
    animate()
        .translationY(if (isTopToBottom) height.toFloat() * translationRatio else -height.toFloat() * translationRatio)
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                gone()
                onAnimationEnd?.invoke()
            }
        })
}

fun View.visibleWithTranslationAnimation(
    duration: Long = VIEW_ANIMATION_DURATION,
    onAnimationEnd: (() -> Unit)? = null
) {
    visible()
    animate()
        .translationY(0f)
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                visible()
                onAnimationEnd?.invoke()
            }
        })
}
