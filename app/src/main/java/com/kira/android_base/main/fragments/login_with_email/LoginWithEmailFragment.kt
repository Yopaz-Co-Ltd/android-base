package com.kira.android_base.main.fragments.login_with_email

import android.widget.Toast
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.FragmentLoginWithEmailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginWithEmailFragment : BaseFragment(R.layout.fragment_login_with_email) {

    private val viewModel: LoginWithEmailViewModel by viewModel()

    override fun initViews() {
        (viewDataBinding as FragmentLoginWithEmailBinding?)?.apply {
            this.viewModel = this@LoginWithEmailFragment.viewModel
            lifecycleOwner = this@LoginWithEmailFragment
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
