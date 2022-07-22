package com.example.android_base_compose.base.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import com.example.android_base_compose.main.theme.Dimen

@Composable
fun LoadingScreen(isShowLoading: Boolean) {
    if (!isShowLoading) return
    Box(
        modifier = modifierFullSize.background(Color.LightGray)
    ) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

val modifierFullSize = Modifier.fillMaxSize()
val modifier4Padding = Modifier
    .fillMaxWidth()
    .padding(Dimen.Space4)
val modifier8Padding = Modifier
    .fillMaxWidth()
    .padding(horizontal = Dimen.Space8)
val modifier16Padding = Modifier
    .fillMaxWidth()
    .padding(horizontal = Dimen.Space16, vertical = Dimen.Space8)

fun Modifier.clearFocus(focusManager: FocusManager): Modifier = composed {
    clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
        focusManager.clearFocus()
    }
}
