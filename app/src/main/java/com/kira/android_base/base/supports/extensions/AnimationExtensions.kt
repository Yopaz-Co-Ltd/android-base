package com.kira.android_base.base.supports.extensions

import android.view.animation.Animation

fun Animation.onAnimationEnd(handle: (animation: Animation?) -> Unit) {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
        }

        override fun onAnimationEnd(animation: Animation?) {
            handle.invoke(animation)
        }

        override fun onAnimationRepeat(animation: Animation?) {
        }
    })
}

fun Animation.onAnimationStart(handle: (animation: Animation?) -> Unit) {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
            handle.invoke(animation)
        }

        override fun onAnimationEnd(animation: Animation?) {
        }

        override fun onAnimationRepeat(animation: Animation?) {
        }
    })
}

fun Animation.onAnimationRepeat(handle: (animation: Animation?) -> Unit) {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
        }

        override fun onAnimationEnd(animation: Animation?) {

        }

        override fun onAnimationRepeat(animation: Animation?) {
            handle.invoke(animation)
        }
    })
}
