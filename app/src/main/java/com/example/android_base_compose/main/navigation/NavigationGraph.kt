package com.example.android_base_compose.main.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android_base_compose.main.ui.login_screen.LoginScreen

@Composable
fun SetUpNavigationGraph(navController: NavHostController) {
    NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.Login.route) {
            LoginScreen()
        }
    }
}
