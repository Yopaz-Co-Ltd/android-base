package com.example.android_base_compose.base.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.android_base_compose.main.theme.AndroidbasecomposeTheme

abstract class BaseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidbasecomposeTheme { InitView() }
        }
    }

    @Composable
    abstract fun InitView()
}
