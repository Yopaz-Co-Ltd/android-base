package com.example.android_base_compose.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.android_base_compose.base.ui.BaseActivity
import com.example.android_base_compose.main.navigation.SetUpNavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Composable
    override fun InitView() {
        val navController = rememberNavController()

        SetUpNavigationGraph(navController = navController)
    }
}
