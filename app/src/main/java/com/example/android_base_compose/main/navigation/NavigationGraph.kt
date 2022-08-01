package com.example.android_base_compose.main.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.android_base_compose.main.ui.account_screen.AccountScreen
import com.example.android_base_compose.main.ui.home_screen.HomeScreen
import com.example.android_base_compose.main.ui.login_screen.LoginScreen

@Composable
fun SetUpNavigationGraph(navController: NavHostController, isLogged: State<Boolean?>) {
    NavHost(
        navController = navController,
        startDestination = if (isLogged.value == true) Screen.Home.route else Screen.Login.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.Login.route) {
            LoginScreen()
        }
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Account.route) {
            AccountScreen()
        }
    }
}
