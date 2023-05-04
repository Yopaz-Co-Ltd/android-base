package com.kira.android_base.main.fragments.login

import android.util.Log
import android.widget.Toast
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    companion object {
        val TAG: String = this::class.java.simpleName
    }

    private val viewModel: LoginViewModel by viewModel()

    override fun initViews() {
        (viewDataBinding as FragmentLoginBinding?)?.apply {
            this.viewModel = this@LoginFragment.viewModel
            lifecycleOwner = this@LoginFragment
            brvLogin.apply {
                setAdapter(LoginRecyclerViewAdapter().apply {
                    list.addAll((1..10).map { "$it" })
                })
                setOnRefreshListener {
                    Log.d(TAG, "initViews: setOnRefreshListener")
                }
            }
        }
    }

    override fun handleObservables() {
        super.handleObservables()

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            mainActivity?.showLoadingDialog(it == true)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            mainActivity?.showErrorDialog(error?.message ?: return@observe)
        }

        viewModel.toastLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it ?: return@observe, Toast.LENGTH_SHORT).show()
        }

        viewModel.isLoggedInLiveData.observe(viewLifecycleOwner) {
            if (it != true) return@observe
            mainActivity?.invalidateAuthState()
        }
    }
}
