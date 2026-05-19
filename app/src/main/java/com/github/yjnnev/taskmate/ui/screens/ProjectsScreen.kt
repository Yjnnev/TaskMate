package com.github.yjnnev.taskmate.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.github.yjnnev.taskmate.ui.components.BottomNav
import com.github.yjnnev.taskmate.ui.components.Header

@Composable
fun ProjectsScreen() {
    // Simple state tracking for bottom navigation simulation
    var currentRoute by remember { mutableStateOf("tasks") }

    Scaffold(
        topBar = { Header() },
        bottomBar = {
            BottomNav (
                currentRoute = currentRoute,
                onNavigate = { currentRoute = it }
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            EmptyStateContent()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProjectsScreenPreview(){
    ProjectsScreen()
}