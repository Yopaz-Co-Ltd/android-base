package com.kira.android_base.base.supports.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import com.kira.android_base.R

object IntentUtils {

    private const val PLAIN_TEXT_TYPE = "text/plain"

    fun shareUrl(context: Context, url: String, title: String = context.getString(R.string.share_using)) {
        ShareCompat.IntentBuilder(context)
            .setType(PLAIN_TEXT_TYPE)
            .setChooserTitle(title)
            .setText(url)
            .startChooser()
    }

    fun openUrl(context: Context, url: String) {
        try {
            context.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
