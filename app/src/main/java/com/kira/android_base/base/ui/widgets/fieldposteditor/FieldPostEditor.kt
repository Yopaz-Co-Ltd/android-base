package com.kira.android_base.base.ui.widgets.fieldposteditor

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.kira.android_base.R
import com.kira.android_base.base.ui.widgets.recyclerview.BaseRecyclerViewAdapter
import com.kira.android_base.databinding.ComponentPostEditorFieldBinding

private var pickMediaRequest: ActivityResultLauncher<PickVisualMediaRequest>? = null

class FieldPostEditor : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        obtainStyledAttributes(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context, attributeSet, defStyleAttr
    ) {
        obtainStyledAttributes(attributeSet, defStyleAttr)
    }

    constructor(
        context: Context,
        pickMedia: ActivityResultLauncher<PickVisualMediaRequest>,
    ) : super(context) {
        pickMediaRequest = pickMedia
    }

    private val binding: ComponentPostEditorFieldBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context), R.layout.component_post_editor_field, this, false
    )


    init {
        addView(binding.root)

        binding.richTextEditor.setPadding(10, 10, 10, 10)
        binding.richToolbar.apply {
            adapter = RichToolBarAdapter().apply {
                list.addAll(toolBarListItem.map {
                    RichToolBarItemModel(
                        it.descriptionItem, it.imageSrc
                    )
                })
                listener = object : BaseRecyclerViewAdapter.Listener<RichToolBarItemModel> {
                    override fun onItemClick(position: Int, t: RichToolBarItemModel) {
                        setActionButton(t)
                    }
                }
            }
            layoutManager = GridLayoutManager(
                context, 10
            )
        }

    }

    private fun obtainStyledAttributes(attributeSet: AttributeSet, defStyleAttr: Int = 0) {
        val typedValue = context.obtainStyledAttributes(
            attributeSet, R.styleable.FieldPostEditor, defStyleAttr, 0
        )
        typedValue.recycle()

    }

    private fun setActionButton(t: RichToolBarItemModel) {
        val richText = binding.richTextEditor
        when (t.descriptionItem) {
            "btn_undo" -> richText.undo()
            "btn_redo" -> richText.redo()
            "btn_bold" -> richText.setBold()
            "btn_italic" -> richText.setItalic()
            "btn_strikethrough" -> richText.setStrikeThrough()
            "btn_underline" -> richText.setUnderline()
            "btn_btn_justify_left" -> richText.setAlignLeft()
            "btn_justify_center" -> richText.setAlignCenter()
            "btn_justify_right" -> richText.setAlignRight()
            "btn_block_quote" -> richText.setBlockquote()
            "btn_heading_1" -> richText.setHeading(1)
            "btn_heading_2" -> richText.setHeading(2)
            "btn_heading_3" -> richText.setHeading(3)
            "btn_heading_4" -> richText.setHeading(4)
            "btn_heading_5" -> richText.setHeading(5)
            "btn_heading_6" -> richText.setHeading(6)
            "btn_indent" -> richText.setIndent()
            "btn_outdent" -> richText.setOutdent()
            "btn_text_color" -> ColorPickerDialog(onSetTextColor = { colorResId ->
                pickColor(colorResId)
            }).show(context)
            "btn_highlight_color" -> BGColorPickerDialog(onSetBGColor = { colorResId ->
                pickBGColor(colorResId)
            }).show(context)
            "btn_insert_image" -> onPickImageOrVideo()
            "btn_insert_video" -> onPickImageOrVideo()
            "btn_unorder_list" -> richText.setBullets()
            "btn_order_list" -> richText.setNumbers()
            "btn_check_box" -> richText.insertTodo()
        }
    }

    private fun pickColor(colorResId: Int) {
        val pickedColor = ContextCompat.getColor(context, colorResId)
        binding.richTextEditor.setTextColor(pickedColor)
    }

    private fun pickBGColor(colorResId: Int) {
        val pickedBGColor = ContextCompat.getColor(context, colorResId)
        binding.richTextEditor.setTextBackgroundColor(pickedBGColor)
    }

    private fun onPickImageOrVideo() {
        binding.richTextEditor.focusEditor()
        pickMediaRequest?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
    }
}
