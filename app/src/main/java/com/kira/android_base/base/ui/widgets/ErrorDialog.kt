package com.kira.android_base.base.ui.widgets

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.kira.android_base.R

class ErrorDialog {

    companion object {
        private var errorDialog: ErrorDialog? = null

        fun show(context: Context, errorMessage: String) {
            if (errorDialog == null) {
                errorDialog = ErrorDialog()
            }
            errorDialog?.show(context, errorMessage)
        }

        fun dismiss() {
            errorDialog?.dismiss()
        }
    }

    private var dialog: AlertDialog? = null

    fun show(context: Context, errorMessage: String) {
        if (dialog == null) {
            dialog = AlertDialog.Builder(context)
                .setTitle(R.string.error)
                .setMessage(errorMessage)
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
        }
        try {
            dialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}
