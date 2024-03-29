package com.kira.android_base.base.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.kira.android_base.R
import com.kira.android_base.base.ui.widgets.ErrorDialog
import com.kira.android_base.base.ui.widgets.LoadingDialog
import org.koin.androidx.scope.ScopeActivity

abstract class BaseActivity(
    @LayoutRes
    private val activityLayoutRes: Int
) : ScopeActivity() {

    private var activityViewDataBinding: ViewDataBinding? = null
    private val errorDialog = ErrorDialog()
    private val loadingDialog = LoadingDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityViewDataBinding = DataBindingUtil.setContentView(this, activityLayoutRes)

        initViews()
        handleObservables()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val isConsumed = super.dispatchTouchEvent(ev)
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            currentFocus?.let {
                if (it !is EditText) return@let
                val outRect = Rect()
                it.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    it.clearFocus()
                }
            }
        }
        return isConsumed
    }

    abstract fun initViews()

    open fun handleObservables() {}

    fun showErrorDialog(
        errorMessage: String,
        title: String = getString(R.string.error),
        positiveText: String = getString(R.string.ok),
        onPositiveClick: () -> Unit = { errorDialog.dismiss() }
    ) = errorDialog.show(this, errorMessage, title, positiveText, onPositiveClick)

    fun dismissErrorDialog() = errorDialog.dismiss()

    fun showLoadingDialog(
        isShow: Boolean = true,
        minTime: Long? = null,
        loadingTypeSprite: Sprite? = null,
        loadingColor: Int? = null
    ) {
        if (isShow) loadingDialog.show(this, minTime, loadingTypeSprite, loadingColor)
        else loadingDialog.dismiss()
    }

    fun getDataBiding() = activityViewDataBinding
}
