package com.kira.android_base.base.supports.utils

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

private const val CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome"

fun openLinkByChromeCustomTabs(context: Context, url: String) {
    CustomTabsIntent.Builder().build().apply {
        intent.setPackage(CUSTOM_TAB_PACKAGE_NAME)
        launchUrl(context, url.toUri())
    }
}
