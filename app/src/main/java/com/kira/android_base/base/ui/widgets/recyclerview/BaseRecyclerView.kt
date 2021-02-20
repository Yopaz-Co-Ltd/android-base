package com.kira.android_base.base.ui.widgets.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kira.android_base.R
import com.kira.android_base.databinding.ComponentBaseRecyclerViewBinding

class BaseRecyclerView : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

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
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        binding.rvBaseRecyclerView.layoutManager = layoutManager
    }

    fun setAdapter(adapter: RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder>) {
        binding.rvBaseRecyclerView.adapter = adapter
    }

    fun setOnRefreshListener(onRefreshListener: SwipeRefreshLayout.OnRefreshListener) {
        binding.srlBaseRecyclerView.isEnabled = true
        binding.srlBaseRecyclerView.setOnRefreshListener(onRefreshListener)
    }

    fun notifyDataSetChanged() {
        binding.rvBaseRecyclerView.adapter?.notifyDataSetChanged()
    }

    fun stopRefreshing() {
        binding.srlBaseRecyclerView.isRefreshing = false
    }
}
