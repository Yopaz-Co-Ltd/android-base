package com.kira.android_base.base.ui.widgets.cardstack

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import com.kira.android_base.R
import com.kira.android_base.databinding.ViewCardItemBinding
import kotlin.math.abs

@SuppressLint("ClickableViewAccessibility")
class CardItemView(context: Context) : CardView(context) {

    companion object {
        private const val TRANSLATION_Y = "translationY"
        private const val THRESHOLD_PIXELS_PER_MILLISECOND = 4f
        private fun getThresholdDistanceMovePixels(context: Context) =
            context.resources.getDimension(R.dimen.space_300)
    }

    private val binding: ViewCardItemBinding
    private val cardStackView by lazy {
        parent as? CardStackView
    }
    private var index = 0
    private var topIndex = 0
    private var yStart = 0f
    private var distanceActionMove = 0f

    init {
        radius = resources.getDimension(R.dimen.space_25)
        binding = ViewCardItemBinding.inflate(LayoutInflater.from(context), this, true)
        setupTouchEventListener()
    }

    private fun setupTouchEventListener() {
        var timeStart = 0L
        setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    yStart = motionEvent.rawY
                    timeStart = System.currentTimeMillis()
                }

                MotionEvent.ACTION_MOVE -> {
                    if (motionEvent.rawY > yStart) return@setOnTouchListener false
                    y = motionEvent.rawY - yStart
                }

                MotionEvent.ACTION_UP -> {
                    val time = System.currentTimeMillis() - timeStart
                    distanceActionMove = abs(motionEvent.rawY - yStart)
                    val velocityY = distanceActionMove / time
                    finishAnimation(time, isReachThreshold(velocityY))
                }

                MotionEvent.ACTION_CANCEL -> {
                    finishAnimation(System.currentTimeMillis() - timeStart)
                }
            }
            return@setOnTouchListener true
        }
    }

    private fun isReachThreshold(velocityY: Float): Boolean =
        velocityY >= THRESHOLD_PIXELS_PER_MILLISECOND &&
                distanceActionMove >= getThresholdDistanceMovePixels(context)

    private fun finishAnimation(timeDuration: Long = 1000, isReachThreshold: Boolean = false) {
        if (isReachThreshold) {
            topIndex = cardStackView?.getNextIndex(index) ?: 0
            changeTranslationZIndex()
            animate().rotationBy(360f).setDuration(200L).withEndAction {
                finishAnimation(timeDuration)
            }.setInterpolator(LinearInterpolator()).start()
            return
        }
        ObjectAnimator.ofFloat(
            this, TRANSLATION_Y, 0f
        ).apply {
            duration = timeDuration
            start()
            doOnEnd {
                cardStackView?.run {
                    if (topIndex != index) {
                        setupTranslationY(topIndex)
                        topIndex = index
                    }
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setup(cardStackItemData: CardStackItemData, index: Int) {
        this.index = index
        this.topIndex = index
        binding.textViewName.text = cardStackItemData.name
        binding.textViewCardName.text = cardStackItemData.cardName
        binding.textViewCardNumber.text = cardStackItemData.cardNumber
        binding.imageViewBackground.setImageResource(cardStackItemData.cardColor)
    }

    private fun changeTranslationZIndex() {
        cardStackView?.run {
            setupTranslationZ(topIndex)
        }
    }
}
