package com.kira.android_base.main.fragments.post_preview

import android.os.Bundle
import android.view.View
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.databinding.FragmentPostEditorBinding
import com.kira.android_base.databinding.FragmentPostPreviewBinding
import com.kira.android_base.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class PostPreviewFragment : BaseFragment(R.layout.fragment_post_preview) {

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun initViews() {
        (viewDataBinding as? FragmentPostEditorBinding)?.run {
            mainViewModel = this@PostPreviewFragment.mainViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = viewDataBinding as FragmentPostPreviewBinding

        val args = arguments?.let { PostPreviewFragmentArgs.fromBundle(it) }
        val postTitle = args?.postTitle
        val listTags = args?.listTags?.toList()
        val contentHtml = args?.contentHtml

        val unencodeHtml = """
        <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
            </head>
            <body style='word-wrap: break-word; overflow-wrap: break-word;'>
              $contentHtml
            </body>
        </html>
    """.trimIndent()

        binding.postTitle.text = postTitle

        binding.tagList.apply {
            adapter = PostPreviewAdapter().apply {
                listTags?.map { it }?.let { list.addAll(it) }
            }
        }

        binding.postContent.isVerticalScrollBarEnabled = false
        binding.postContent.isHorizontalScrollBarEnabled = false
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
