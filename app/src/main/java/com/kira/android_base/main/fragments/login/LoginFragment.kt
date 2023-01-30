package com.kira.android_base.main.fragments.login

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kira.android_base.R
import com.kira.android_base.base.supports.extensions.navigateTo
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.FragmentLoginBinding
import com.kira.android_base.main.fragments.home.HomeArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    companion object {
        val TAG: String = this::class.java.simpleName
    }

    private val viewModel by viewModels<LoginViewModel>()

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

        viewModel.userLiveData.observe(viewLifecycleOwner) {
            findNavController().navigateTo(
                LoginFragmentDirections.actionLoginFragmentToHomeFragment(
                    HomeArgs(it?.name, it?.age)
                )
            )
        }
    }
}
