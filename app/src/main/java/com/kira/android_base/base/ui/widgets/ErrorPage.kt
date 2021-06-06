package com.kira.android_base.base.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.kira.android_base.R
import com.kira.android_base.databinding.LayoutErrorScreenBinding

class ErrorPage @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private var binding: LayoutErrorScreenBinding? = null

    init {
        elevation = resources.getDimension(R.dimen.space_12)
        binding = LayoutErrorScreenBinding.inflate(LayoutInflater.from(context)).apply {
            addView(root)
        }
    }

    fun setOnRefreshClickListener(onRefresh: () -> Unit) {
        binding?.bRefresh?.setOnClickListener {
            onRefresh()
        }
    }
}
