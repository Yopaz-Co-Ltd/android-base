package com.kira.android_base.main.fragments.home

import androidx.fragment.app.activityViewModels
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.base.ui.widgets.cardstack.CardStackItemData
import com.kira.android_base.databinding.FragmentHomeBinding
import com.kira.android_base.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun initViews() {
        (viewDataBinding as? FragmentHomeBinding)?.run {
            mainViewModel = this@HomeFragment.mainViewModel
            lifecycleOwner = viewLifecycleOwner
            initViewAnimations()
        }
        mainViewModel.getLocalUser()
    }

    private fun initViewAnimations() {
        (viewDataBinding as? FragmentHomeBinding)?.run {
            cardStackView.setup(
                listOf(
                    CardStackItemData(
                        name = "nguyen tuan anh",
                        cardNumber = "1234-5678-9101-1121",
                        cardName = "VISA",
                        cardColor = R.color.chantilly
                    ),
                    CardStackItemData(
                        name = "nguyen tuan anh",
                        cardNumber = "1234-5678-9101-1121",
                        cardName = "VISA",
                        cardColor = R.color.color1
                    ),
                    CardStackItemData(
                        name = "nguyen tuan anh",
                        cardNumber = "1234-5678-9101-1121",
                        cardName = "VISA",
                        cardColor = R.color.color2
                    ),
                    CardStackItemData(
                        name = "nguyen tuan anh",
                        cardNumber = "1234-5678-9101-1121",
                        cardName = "VISA",
                        cardColor = R.color.color3
                    ),
                    CardStackItemData(
                        name = "nguyen tuan anh",
                        cardNumber = "1234-5678-9101-1121",
                        cardName = "VISA",
                        cardColor = R.color.color4
                    ),
                    CardStackItemData(
                        name = "nguyen tuan anh",
                        cardNumber = "1234-5678-9101-1121",
                        cardName = "VISA",
                        cardColor = R.color.color5
                    )
                )
            )
        }
    }
}
