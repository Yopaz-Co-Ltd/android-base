package com.example.android_base_compose.main.navigation

import com.example.android_base_compose.R

sealed class Screen(val route: String, val icon: Int, val title: String) {
    object Home : Screen("home_screen", R.drawable.baseline_home_24, "Home")
    object Login : Screen("login_screen", 0, "")
    object Account : Screen("account_screen", R.drawable.baseline_account_circle_24, "Account")
}
