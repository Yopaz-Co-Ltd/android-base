package com.kira.android_base.main.fragments.post_editor

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import com.kira.android_base.R
import com.kira.android_base.base.ui.BaseFragment
import com.kira.android_base.base.ui.widgets.recyclerview.BaseRecyclerViewAdapter
import com.kira.android_base.databinding.FragmentPostEditorBinding
import com.kira.android_base.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PostEditorFragment : BaseFragment(R.layout.fragment_post_editor) {

    companion object {
        const val MAX_TAG_NUMBER = 5
    }

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun initViews() {
        (viewDataBinding as? FragmentPostEditorBinding)?.run {
            mainViewModel = this@PostEditorFragment.mainViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = viewDataBinding as FragmentPostEditorBinding
        val tagInputView = binding.tagInput

        binding.tagList.run {
            adapter = TagListRecyclerViewAdapter().apply {
                listener = object : BaseRecyclerViewAdapter.Listener<String> {
                    override fun onItemClick(position: Int, t: String) {
                        removeTagList(binding, position)
                    }
                }
            }
        }

        tagInputView.setOnKeyListener { _, keyCode, event ->
            if ((keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_COMMA) && event.action == KeyEvent.ACTION_DOWN) {
                addTagList(binding)
                true
            } else {
                false
            }
        }

        tagInputView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val adapter = binding.tagList.adapter as TagListRecyclerViewAdapter
                if (adapter.list.isNotEmpty()) {
                    tagInputView.inputType = InputType.TYPE_CLASS_TEXT
                    tagInputView.hint = getString(R.string.input_tag_hint)
                } else {
                    tagInputView.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                    tagInputView.hint = getString(R.string.initial_input_tag_hint)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun addTagList(binding: FragmentPostEditorBinding) {
        val currentTagInput: String = binding.tagInput.text.toString().trim()
        val adapter = binding.tagList.adapter as? TagListRecyclerViewAdapter
        adapter?.let {
            if (validateInputTag(currentTagInput, it.list)) {
                it.updateInsertData(currentTagInput)
                binding.tagInput.setText("")
                setMultipleLine(binding, it.list)
                binding.tagList.smoothScrollToPosition(it.itemCount)
                validateTagLength(binding, it.list)
            }
        }
    }

    private fun removeTagList(binding: FragmentPostEditorBinding, position: Int) {
        val adapter = binding.tagList.adapter as? TagListRecyclerViewAdapter
        adapter?.updateRemoveData(position)
        adapter?.let {
            setMultipleLine(binding, it.list)
            validateTagLength(binding, it.list)
        }
    }

    private fun validateTagLength(binding: FragmentPostEditorBinding, list: MutableList<String>) {
        if (list.size >= MAX_TAG_NUMBER) {
            binding.tagInput.visibility = View.GONE
            binding.tagList.layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
            binding.tagListContainer.layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
        } else {
            binding.tagInput.visibility = View.VISIBLE
            binding.tagList.layoutParams?.width = ViewGroup.LayoutParams.WRAP_CONTENT
            binding.tagListContainer.layoutParams?.width = ViewGroup.LayoutParams.WRAP_CONTENT
        }
    }

    private fun setMultipleLine(binding: FragmentPostEditorBinding, list: MutableList<String>) {
        if (list.isNotEmpty()) {
            binding.tagInput.inputType = InputType.TYPE_CLASS_TEXT
            binding.tagInput.hint = getText(R.string.input_tag_hint)
        } else {
            binding.tagInput.hint = getText(R.string.initial_input_tag_hint)
            binding.tagInput.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
        }
    }

    private fun validateInputTag(currentText: String, list: MutableList<String>): Boolean =
        currentText.isNotEmpty() && !list.contains(currentText)

}
