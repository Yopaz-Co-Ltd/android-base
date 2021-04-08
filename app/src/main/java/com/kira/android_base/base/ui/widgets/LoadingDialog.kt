package com.kira.android_base.base.ui.widgets

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.github.ybq.android.spinkit.sprite.Sprite
import com.kira.android_base.databinding.DialogLoadingBinding

class LoadingDialog {

    companion object {
        private var loadingDialog: LoadingDialog? = null
        fun show(
            context: Context?,
            minTime: Long? = null,
            loadingTypeSprite: Sprite? = null,
            loadingColor: Int? = null
        ) {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog()
            }
            loadingDialog?.show(context, minTime, loadingTypeSprite, loadingColor)
        }

        fun dismiss() {
            loadingDialog?.dismiss()
        }
    }

    private var dialog: Dialog? = null
    private var dialogLoadingBinding: DialogLoadingBinding? = null
    private var markedTime = 0L

    fun show(
        context: Context?,
        minTime: Long? = null,
        loadingTypeSprite: Sprite? = null,
        loadingColor: Int? = null
    ) {
        if (dialogLoadingBinding == null) {
            dialogLoadingBinding = DialogLoadingBinding.inflate(LayoutInflater.from(context))
        }
        if (dialog == null) {
            dialog = context?.let {
                Dialog(it).apply {
                    requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialogLoadingBinding?.let {
                        setContentView(it.root)
                    }
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    setCancelable(false)
                }
            }
        }
        loadingTypeSprite?.let {
            dialogLoadingBinding?.skvLoading?.setIndeterminateDrawable(it)
        }
        loadingColor?.let {
            dialogLoadingBinding?.skvLoading?.setColor(it)
        }
        minTime?.let {
            markedTime = System.currentTimeMillis() + it
        }
        try {
            dialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismiss() {
        val retainedTime = markedTime - System.currentTimeMillis()
        if (retainedTime > 0L) {
            dialogLoadingBinding?.skvLoading?.postDelayed({
                dialog?.dismiss()
                markedTime = 0L
            }, retainedTime)
            return
        }
        dialog?.dismiss()
    }
}
