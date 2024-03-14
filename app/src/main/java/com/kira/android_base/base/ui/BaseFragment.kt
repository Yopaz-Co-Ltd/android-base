package com.kira.android_base.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.kira.android_base.main.MainActivity

abstract class BaseFragment<VB: ViewDataBinding>(private val layoutResId: Int) : Fragment() {

    protected var mainActivity: MainActivity? = null
    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    protected abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as? MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DataBindingUtil.inflate<VB>(
            inflater, layoutResId, container, false
        ).apply {
            _binding = this
            return root
        }
    }

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

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    abstract fun initViews()

    open fun handleObservables() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            mainActivity?.showLoadingDialog(it == true)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            mainActivity?.showErrorDialog(error?.message ?: return@observe)
        }

        viewModel.toastLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it ?: return@observe, Toast.LENGTH_SHORT).show()
        }
    }

    /*
    * this function is call onViewCreated and onHiddenChanged (hidden = false)
    * */
    open fun onViewCreatedOrShow() {}
}
