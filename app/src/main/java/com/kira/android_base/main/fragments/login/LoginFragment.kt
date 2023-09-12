package com.kira.android_base.main.fragments.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
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
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = viewDataBinding as FragmentLoginBinding

        binding.buttonNavigateToSignUp.setOnClickListener{
            navigateToSignUp(it)
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

    fun navigateToSignUp(view: View) {
        val action = LoginFragmentDirections.actionLoginFragmentToSignUpWithEmailFragment()
        view.findNavController().navigate(action)
    }
}
