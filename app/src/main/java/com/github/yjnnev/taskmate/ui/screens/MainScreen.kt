package com.github.yjnnev.taskmate.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.yjnnev.taskmate.ui.components.BottomNav
import com.github.yjnnev.taskmate.ui.components.Header
import com.github.yjnnev.taskmate.ui.navigation.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    // 1. Create the NavController
    val navController = rememberNavController()

    // 2. Observe the current route to update the BottomNav UI
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Tasks.route

    Scaffold(
        topBar = { 
            Header(
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            )
        },
        bottomBar = {
            // Only show bottom bar on top-level screens
            if (currentRoute in listOf(Screen.Tasks.route, Screen.Projects.route)) {
                BottomNav(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        // 4. The NavHost swaps out the content based on the route
        NavHost(
            navController = navController,
            startDestination = Screen.Tasks.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(Screen.Tasks.route) {
                TasksScreen()
            }
            composable(Screen.Projects.route) {
                ProjectsScreenContainer()
            }
            composable(Screen.Theme.route) {
                ThemeScreen(onBack = { navController.popBackStack() })
            }
            composable(Screen.Profile.route) {
                ProfileScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
