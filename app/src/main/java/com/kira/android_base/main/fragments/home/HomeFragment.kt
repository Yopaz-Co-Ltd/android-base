package com.kira.android_base.main.fragments.home

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.base.ui.widgets.recyclerview.BaseRecyclerViewAdapter
import com.kira.android_base.databinding.FragmentHomeBinding
import com.kira.android_base.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

//TODO REMOVE FAKE DATA
val data = listOf(
    PostItemHomeModel("Trương Nhật 1", "Ha Noi", "11 August, 2023", "Hello World 1"),
    PostItemHomeModel("Trương Nhật 2", "Ha Noi", "12 August, 2023", "Hello World 2"),
    PostItemHomeModel("Trương Nhật 3", "Ha Noi", "13 August, 2023", "Hello World 3"),
    PostItemHomeModel("Trương Nhật 4", "Ha Noi", "14 August, 2023", "Hello World 4"),
    PostItemHomeModel("Trương Nhật 5", "Ha Noi", "15 August, 2023", "Hello World 5"),
    PostItemHomeModel("Trương Nhật 6", "Ha Noi", "16 August, 2023", "Hello World 6"),
    PostItemHomeModel("Trương Nhật 7", "Ha Noi", "17 August, 2023", "Hello World 7"),
    PostItemHomeModel("Trương Nhật 8", "Ha Noi", "18 August, 2023", "Hello World 8"),
)

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val mainViewModel: MainViewModel by sharedViewModel()

    private var postListView: RecyclerView? = null
    private var postListData: List<PostItemHomeData> = emptyList()

    override fun initViews() {
        (viewDataBinding as? FragmentHomeBinding)?.run {
            mainViewModel = this@HomeFragment.mainViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        mainViewModel.getLocalUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = viewDataBinding as FragmentHomeBinding

        binding.listItemHome.apply {
            adapter = ListPostHomeRecyclerViewAdapter().apply {
                list.addAll(data.map {
                    PostItemHomeModel(
                        it.user,
                        it.place,
                        it.time,
                        it.title
                    )
                })
                listener = object : BaseRecyclerViewAdapter.Listener<PostItemHomeModel> {
                    override fun onItemClick(position: Int, t: PostItemHomeModel) {
                        navigateToPostDetail(view)
                    }
                }
            }
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun navigateToPostDetail(view: View) {
        val action = HomeFragmentDirections.actionHomeFragmentToPostDetailFragment()
        view.findNavController().navigate(action)
    }
}
