package com.kira.android_base.main.fragments.login

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    companion object {
        val TAG: String = this::class.java.simpleName
    }

    override val viewModel: LoginViewModel by viewModels()

    override fun initViews() {
        binding.apply {
            this.viewModel = this@LoginFragment.viewModel
            lifecycleOwner = this@LoginFragment
        }
    }

    override fun handleObservables() {
        super.handleObservables()

        viewModel.isLoggedInLiveData.observe(viewLifecycleOwner) {
            if (it != true) return@observe
            mainActivity?.invalidateAuthState()
        }
    }
}
