package com.kira.android_base.main.fragments.post_detai

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.FragmentPostDetailBinding
import com.kira.android_base.main.MainViewModel
import com.kira.android_base.main.fragments.home.PostItemHomeModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PostDetailFragment : BaseFragment(R.layout.fragment_post_detail) {

    //TODO REMOVE
    val unencodeHtml = """
        <html>
        <head>
          <title>Hướng dẫn cài đặt Ruby on Rails trên Windows</title>
        </head>
        <body>
          <h1>How To Install #RubyonRails on Window 7/8/10/11 -> Tutorial 2022"</h1>

          <h2>Bước 1: Cài đặt Ruby</h2>
        </body>
        </html>
    """.trimIndent()

    //TODO REMOVE
    val data = listOf(
        SuggestionPostModel("Trương Nhật 1", "Ha Noi", "11 August, 2023", "Hello World 1"),
        SuggestionPostModel("Trương Nhật 2", "Ha Noi", "12 August, 2023", "Hello World 2"),
        SuggestionPostModel("Trương Nhật 3", "Ha Noi", "13 August, 2023", "Hello World 3"),
        SuggestionPostModel("Trương Nhật 4", "Ha Noi", "14 August, 2023", "Hello World 4"),
        SuggestionPostModel("Trương Nhật 5", "Ha Noi", "15 August, 2023", "Hello World 5"),
        SuggestionPostModel("Trương Nhật 6", "Ha Noi", "16 August, 2023", "Hello World 6"),
        SuggestionPostModel("Trương Nhật 7", "Ha Noi", "17 August, 2023", "Hello World 7"),
        SuggestionPostModel("Trương Nhật 8", "Ha Noi", "18 August, 2023", "Hello World 8"),
    )

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun initViews() {
        (viewDataBinding as? FragmentPostDetailBinding)?.run {
            mainViewModel = this@PostDetailFragment.mainViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = viewDataBinding as FragmentPostDetailBinding
        binding.suggesstionPostList.isNestedScrollingEnabled = false

        binding.suggesstionPostList.apply {
//            adapter = SuggestionPostRecyclerViewAdapter().apply {
//                list.addAll(data.map {
//                    PostItemHomeModel(it.user, it.place, it.time, it.title)
//                })
//            }
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
        }

        binding.postContent.isVerticalScrollBarEnabled = false
        val encodeHtml = android.util.Base64.encodeToString(
            unencodeHtml.toByteArray(), android.util.Base64.NO_PADDING
        )
        binding.postContent.loadData(
            encodeHtml,
            getString(R.string.web_view_mime_type),
            getString(R.string.web_view_encoding)
        )
    }
}
