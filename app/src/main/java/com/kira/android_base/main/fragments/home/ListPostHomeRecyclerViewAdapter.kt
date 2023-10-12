package com.kira.android_base.main.fragments.home

import com.kira.android_base.R
import com.kira.android_base.base.ui.widgets.recyclerview.BaseRecyclerViewAdapter
import com.kira.android_base.databinding.ItemHomeRecyclerViewBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ListPostHomeRecyclerViewAdapter :
    BaseRecyclerViewAdapter<PostItemHomeModel>(R.layout.item_home_recycler_view) {

    companion object {
        private const val ORIGIN_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        private const val DATE_FORMAT = "MMM dd, yyyy"
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        (holder.viewDataBinding as ItemHomeRecyclerViewBinding?)?.apply {
            userItemHome.text = currentItem.user.name
            timeItemHome.text = convertStringToDate(currentItem.created_at)
            titleItemHome.text = currentItem.title
            //TODO REMOVE FAKE DATA
            placeItemHome.text = "Hà Nội"

            postItemHome.setOnClickListener {
                listener?.onItemClick(position, list[position])
            }
        }
    }

    fun convertStringToDate(dateString: String): String {
        val originDateFormat = SimpleDateFormat(ORIGIN_DATE_FORMAT, Locale.getDefault())
        val date: Date = originDateFormat.parse(dateString)

        val newDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return newDateFormat.format(date)
    }
}
