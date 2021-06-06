package com.kira.android_base.main.fragments.login

import android.util.Log
import android.view.View
import android.widget.Toast
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.base.ui.widgets.ErrorDialog
import com.kira.android_base.base.ui.widgets.LoadingDialog
import com.kira.android_base.databinding.FragmentLoginBinding
import com.kira.android_base.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment(R.layout.fragment_login), View.OnClickListener {

    companion object {
        val TAG: String = this::class.java.simpleName
    }

    private val viewModel: LoginViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun initViews() {
        (viewDataBinding as FragmentLoginBinding?)?.apply {
            onClickListener = this@LoginFragment
            brvLogin.apply {
                setAdapter(LoginRecyclerViewAdapter().apply {
                    list.addAll((1..10).map { "$it" })
                })
                setOnRefreshListener {
                    Log.d(TAG, "initViews: setOnRefreshListener")
                }
            }
        }
        mainActivity?.hideStatusBar()
    }

    override fun handleObservables() {
        super.handleObservables()
        viewModel.loginLiveData.observe(viewLifecycleOwner) {
            (viewDataBinding as FragmentLoginBinding?)?.loginResponse = it
            LoadingDialog.dismiss()
        }

        viewModel.insertLocalUserLiveData.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) return@observe
            Toast.makeText(context, "Insert success ${it.first()}", Toast.LENGTH_SHORT).show()
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            LoadingDialog.dismiss()
            context?.let {
                ErrorDialog.show(it, error.message)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.b_login -> {
                context?.let {
                    LoadingDialog.show(it)
                }
                viewModel.login(
                    loginCallback = {
                        Log.d(TAG, "loginCallback: $it")
                    },
                    insertLocalUserCallback = {
                        Log.d(TAG, "insertLocalUserCallback: $it")
                    }
                )
            }
        }
    }
}
