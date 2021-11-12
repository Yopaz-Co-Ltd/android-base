package com.kira.android_base.base.ui.widgets.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kira.android_base.R
import com.kira.android_base.databinding.ComponentBaseRecyclerViewBinding

class BaseRecyclerView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        obtainStyledAttributes(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        obtainStyledAttributes(attributeSet, defStyleAttr)
    }

    private val binding: ComponentBaseRecyclerViewBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.component_base_recycler_view,
        this,
        false
    )

    init {
        addView(binding.root)
        binding.rvBaseRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.srlBaseRecyclerView.isEnabled = false
    }

    private fun obtainStyledAttributes(attributeSet: AttributeSet, defStyleAttr: Int = 0) {
        val typedValue = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.BaseRecyclerView,
            defStyleAttr,
            0
        )
        val isEnablePagerSnapHelper =
            typedValue.getBoolean(R.styleable.BaseRecyclerView_enable_pager_snap_helper, false)
        if (isEnablePagerSnapHelper) enablePagerSnapHelper()
        typedValue.recycle()
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        binding.rvBaseRecyclerView.layoutManager = layoutManager
    }

    fun getLayoutManager() = binding.rvBaseRecyclerView.layoutManager

    fun setAdapter(adapter: RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder>) {
        binding.rvBaseRecyclerView.adapter = adapter
    }

    fun getAdapter() = binding.rvBaseRecyclerView.adapter as RecyclerView.Adapter<*>

    fun setOnRefreshListener(onRefreshListener: SwipeRefreshLayout.OnRefreshListener) {
        binding.srlBaseRecyclerView.isEnabled = true
        binding.srlBaseRecyclerView.setOnRefreshListener(onRefreshListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyDataSetChanged() {
        binding.rvBaseRecyclerView.adapter?.notifyDataSetChanged()
    }

    fun stopRefreshing() {
        binding.srlBaseRecyclerView.isRefreshing = false
    }

    fun addOnScrollListener(onScrollListener: RecyclerView.OnScrollListener) {
        binding.rvBaseRecyclerView.addOnScrollListener(onScrollListener)
    }

    fun removeOnScrollListener(onScrollListener: RecyclerView.OnScrollListener) {
        binding.rvBaseRecyclerView.removeOnScrollListener(onScrollListener)
    }

    private fun enablePagerSnapHelper() {
        PagerSnapHelper().attachToRecyclerView(binding.rvBaseRecyclerView)
    }
}
