package com.kira.android_base.main.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.base.ui.widgets.recyclerview.BaseRecyclerViewAdapter
import com.kira.android_base.databinding.FragmentHomeBinding
import com.kira.android_base.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val homeViewModel: HomeViewModel by sharedViewModel()

    private val listPostData: MutableList<PostItemHomeModel> = mutableListOf()

    override fun initViews() {
        (viewDataBinding as? FragmentHomeBinding)?.run {
            mainViewModel = this@HomeFragment.mainViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        mainViewModel.getLocalUser()
        homeViewModel.getListPostHome()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = viewDataBinding as FragmentHomeBinding

        binding.swipeRefreshHome.setProgressViewOffset(false, 0, 200)

    }

    override fun handleObservables() {
        super.handleObservables()

        homeViewModel.mutableListPost.observe(viewLifecycleOwner, Observer {
            updatePostList(it)
        })

        mainViewModel.loadingLiveData.observe(viewLifecycleOwner) {
            mainActivity?.showLoadingDialog(it == true)

        }
    }

    private fun updatePostList(data: List<PostItemHomeModel>?) {
        if (data != null) {
            listPostData.addAll(data)
        }

        val binding = viewDataBinding as FragmentHomeBinding

        binding.listItemHome.apply {
            adapter = ListPostHomeRecyclerViewAdapter().apply {
                list.addAll(listPostData.map {
                    PostItemHomeModel(
                        it.id,
                        it.user_id,
                        it.title,
                        it.content,
                        it.created_at,
                        it.updated_at,
                        it.deleted_at,
                        it.user,
                        it.tags
                    )
                })
                listener = object : BaseRecyclerViewAdapter.Listener<PostItemHomeModel> {
                    override fun onItemClick(position: Int, t: PostItemHomeModel) {
                        navigateToPostDetail(requireView())
                    }
                }
            }
        }
    }

    private fun navigateToPostDetail(view: View) {
        val action = HomeFragmentDirections.actionHomeFragmentToPostDetailFragment()
        view.findNavController().navigate(action)
    }
}
