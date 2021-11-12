package com.kira.android_base.base.ui.widgets

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.kira.android_base.R

class ErrorDialog {

    private var dialog: AlertDialog? = null

    fun show(
        context: Context,
        errorMessage: String,
        title: String,
        positiveText: String,
        onPositiveClick: () -> Unit
    ) {
        if (dialog == null) {
            dialog =
                AlertDialog.Builder(context)
                    .setPositiveButton(R.string.ok) { dialog, _ -> dialog?.dismiss() }
                    .create()
        }
        dialog?.setMessage(errorMessage)
        dialog?.setTitle(title)
        dialog?.getButton(DialogInterface.BUTTON_POSITIVE)?.apply {
            text = positiveText
            setOnClickListener { onPositiveClick() }
        }
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}
