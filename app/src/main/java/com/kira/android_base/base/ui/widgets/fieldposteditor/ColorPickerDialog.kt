package com.kira.android_base.base.ui.widgets.fieldposteditor

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kira.android_base.R
import com.kira.android_base.base.ui.widgets.recyclerview.BaseRecyclerViewAdapter
import com.kira.android_base.databinding.DialogColorPickerBinding

class ColorPickerDialog(
    private var onSetTextColor: (t: Int) -> Unit,
) {
    private var dialog: Dialog? = null
    private var dialogColorPickerBinding: DialogColorPickerBinding? = null

    private val colorList: List<Int> = listOf(
        R.color.black,
        R.color.purple,
        R.color.blue,
        R.color.green,
        R.color.red,
        R.color.light_silver,
        R.color.pink,
        R.color.steel_blue,
        R.color.brown,
        R.color.yellow,
        R.color.sky_blue,
        R.color.turquoise,
        R.color.orange,
    )

    fun show(
        context: Context?,
    ) {
        var currentColor: Int = R.color.black
        if (dialogColorPickerBinding == null) {
            dialogColorPickerBinding =
                DialogColorPickerBinding.inflate(LayoutInflater.from(context))
        }
        if (dialog == null) {
            dialog = context?.let { it ->
                Dialog(it).apply {
                    setContentView(R.layout.dialog_color_picker)
                    window?.setBackgroundDrawableResource(android.R.color.transparent)
                }
            }
            val dialogTitle = dialog?.findViewById<TextView>(R.id.dialog_title)
            dialogTitle?.setText(R.string.dialog_text_color)
            val colorPickerListView = dialog?.findViewById<RecyclerView>(R.id.color_picker_list)
            colorPickerListView?.isNestedScrollingEnabled = false
            colorPickerListView?.apply {
                adapter = ColorPickerAdapter().apply {
                    list.addAll(colorList.map { it })
                    listener = object : BaseRecyclerViewAdapter.Listener<Int> {
                        override fun onItemClick(position: Int, t: Int) {
                            currentColor = t
                        }
                    }
                }
            }

            dialog?.findViewById<Button>(R.id.btn_positive_color_picker)?.setOnClickListener {
                onSetTextColor(currentColor)
                dismiss()
            }

            dialog?.findViewById<Button>(R.id.btn_negative_color_picker)?.setOnClickListener {
                dismiss()
            }

        }
        dialog?.show()
    }


    private fun dismiss() {
        dialog?.dismiss()
    }
}
