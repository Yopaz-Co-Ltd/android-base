package com.kira.android_base.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.kira.android_base.main.MainActivity

abstract class BaseFragment(private val layoutResId: Int) : Fragment() {

    var mainActivity: MainActivity? = null
    var viewDataBinding: ViewDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DataBindingUtil.inflate<ViewDataBinding>(
            inflater, layoutResId, container, false
        ).apply {
            viewDataBinding = this
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        handleObservables()
    }

    abstract fun initViews()

    abstract fun handleObservables()
}
