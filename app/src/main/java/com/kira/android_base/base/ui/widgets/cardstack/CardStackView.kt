package com.kira.android_base.base.ui.widgets.cardstack

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.forEachIndexed
import com.kira.android_base.R

class CardStackView(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    companion object {
        fun getTranslationYCardItem(context: Context) =
            context.resources.getDimension(R.dimen.space_5)
    }

    fun setup(list: List<CardStackItemData>) {
        removeAllViews()
        list.forEachIndexed { index, data ->
            val cardItemView = CardItemView(context).apply {
                this.setup(data, index)
                this.translationY = getTranslationYCardItem(context) * (0 - index)
                this.translationZ = (0f - index)
            }
            addView(cardItemView)
        }
    }

    fun setupTranslationY(topIndex: Int) {
        forEachIndexed { index, view ->
            (getTranslationYCardItem(context) * getTranslationY(topIndex - index)).also {
                view.translationY = it
            }
        }
    }

    fun setupTranslationZ(topIndex: Int) {
        forEachIndexed { index, view ->
            ViewCompat.setTranslationZ(
                view,
                getTranslationZ((topIndex - index).toFloat())
            )
        }
    }

    private fun getTranslationY(spatialDistanceIndex: Int) =
        spatialDistanceIndex.takeIf { spatialDistanceIndex <= 0 }
            ?: (spatialDistanceIndex - childCount)

    private fun getTranslationZ(spatialDistanceIndex: Float): Float =
        spatialDistanceIndex.takeIf { spatialDistanceIndex <= 0 }
            ?: (spatialDistanceIndex - childCount)

    fun getNextIndex(currentIndex: Int) =
        (currentIndex + 1).takeIf { currentIndex + 1 < childCount } ?: 0
}
