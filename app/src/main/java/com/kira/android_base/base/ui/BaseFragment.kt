package com.kira.android_base.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<VDB : ViewDataBinding>(
    private val inflateMethod: (LayoutInflater) -> VDB
) : Fragment() {

    protected val binding: VDB by lazy { inflateMethod(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = binding.root

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (!hidden) onViewCreatedOrShow()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        onViewCreatedOrShow()
        handleObservables()
    }

    abstract fun initViews()

    open fun handleObservables() {}

    /*
    * this function is call onViewCreated and onHiddenChanged (hidden = false)
    * */
    open fun onViewCreatedOrShow() {}
}
