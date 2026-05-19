package com.github.yjnnev.taskmate.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.yjnnev.taskmate.R
import com.github.yjnnev.taskmate.ui.components.BottomNav
import com.github.yjnnev.taskmate.ui.components.EmptyState
import com.github.yjnnev.taskmate.ui.components.Header
import com.github.yjnnev.taskmate.ui.navigation.Screen

@Composable
fun MainScreen() {
    // 1. Create the NavController
    val navController = rememberNavController()

    // 2. Observe the current route to update the BottomNav UI
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Tasks.route

    Scaffold(
        topBar = { Header() },
        bottomBar = {
            BottomNav(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    // 3. Navigate to the selected screen
                    navController.navigate(route) {
                        // These lines prevent building a massive backstack if the user taps tabs repeatedly
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        // 4. The NavHost swaps out the content based on the route
        NavHost(
            navController = navController,
            startDestination = Screen.Tasks.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding()
                )
        ) {
            composable(Screen.Tasks.route) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    EmptyState(
                        title = "No Tasks Yet",
                        subtitle = "Your daily tasks will appear here. Start by creating a new one!",
                        buttonText = "Add Task",
                        iconRes = R.drawable.ic_ghost,
                        onActionClick = { /* TODO */ }
                    )
                }
            }
            composable(Screen.Projects.route) {
                ProjectsScreenContainer()
            }
        }
    }
}