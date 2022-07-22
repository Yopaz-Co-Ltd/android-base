package com.example.android_base_compose.main.navigation

sealed class Screen(val route: String, val icon: Int, val title: String) {
    object Login : Screen("login_screen", 0, "")
}
