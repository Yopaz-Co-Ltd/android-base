package com.example.android_base_compose.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.android_base_compose.base.ui.BaseActivity
import com.example.android_base_compose.main.navigation.Screen
import com.example.android_base_compose.main.navigation.SetUpNavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Composable
    override fun InitView() {
        val mainViewModel: MainViewModel = hiltViewModel()
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val isShowBottomBar = mainViewModel.isLoggedState.collectAsState(null)
        val items = listOf(Screen.Home, Screen.Account)

        Scaffold(bottomBar = {
            SetUpBottomBar(
                    isShowBottomBar = isShowBottomBar,
                    navController = navController,
                    screenList = items,
                    navBackStackEntry = navBackStackEntry ?: return@Scaffold
            )
        }) {
            Box(Modifier.padding(it)) {
                SetUpNavigationGraph(navController = navController, isShowBottomBar)
            }
        }
    }
}

@Composable
fun SetUpBottomBar(
        isShowBottomBar: State<Boolean?>,
        navController: NavHostController,
        screenList: List<Screen>,
        navBackStackEntry: NavBackStackEntry
) {
    if (isShowBottomBar.value == false) return
    BottomNavigation(
            backgroundColor = MaterialTheme.colors.surface
    ) {
        val currentDestination = navBackStackEntry.destination
        screenList.forEach { screen ->
            BottomNavigationItem(
                    selected = currentDestination.hierarchy.any { it.route == screen.route },
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(painter = painterResource(id = screen.icon), contentDescription = "")
                    },
                    label = { Text(text = screen.title) },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = Color.LightGray
            )
        }
    }
}
