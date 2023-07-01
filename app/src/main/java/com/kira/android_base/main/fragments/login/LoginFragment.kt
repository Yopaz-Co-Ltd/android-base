package com.kira.android_base.main.fragments.login

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.FragmentLoginBinding
import com.kira.android_base.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    companion object {
        val TAG: String = this::class.java.simpleName
    }

    private val mainActivity: MainActivity? by lazy { activity as? MainActivity }
    private val viewModel by viewModels<LoginViewModel>()

    override fun initViews() {
        binding.run {
            this.viewModel = this@LoginFragment.viewModel
            lifecycleOwner = this@LoginFragment
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
