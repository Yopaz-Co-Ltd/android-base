package com.kira.android_base.main.fragments.home

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.FragmentHomeBinding
import com.kira.android_base.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

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
        postListView = view.findViewById(R.id.list_item_home)

        //TODO REMOVE
        val data = listOf(
            PostItemHomeData("Trương Nhật 1", "Ha Noi", "11 August, 2023", "Hello World 1"),
            PostItemHomeData("Trương Nhật 2", "Ha Noi", "12 August, 2023", "Hello World 2"),
            PostItemHomeData("Trương Nhật 3", "Ha Noi", "13 August, 2023", "Hello World 3"),
            PostItemHomeData("Trương Nhật 4", "Ha Noi", "14 August, 2023", "Hello World 4"),
            PostItemHomeData("Trương Nhật 5", "Ha Noi", "15 August, 2023", "Hello World 5"),
            PostItemHomeData("Trương Nhật 6", "Ha Noi", "16 August, 2023", "Hello World 6"),
            PostItemHomeData("Trương Nhật 7", "Ha Noi", "17 August, 2023", "Hello World 7"),
            PostItemHomeData("Trương Nhật 8", "Ha Noi", "18 August, 2023", "Hello World 8"),
        )

        postListData = data

        val adapter = ListPostHomeRecyclerViewAdapter(postListData, object : ListPostHomeRecyclerViewInterface {
            override fun navigateToPostDetail(position: Int) {
                    val action = HomeFragmentDirections.actionHomeFragmentToPostDetailFragment()
                    view.findNavController().navigate(action)
            }
        })

        postListView?.adapter = adapter

        postListView?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }


}
