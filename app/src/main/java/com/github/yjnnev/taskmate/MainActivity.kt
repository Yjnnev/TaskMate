package com.github.yjnnev.taskmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle // 1. Add this import
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import com.github.yjnnev.taskmate.ui.screens.HomeScreen
import com.github.yjnnev.taskmate.ui.screens.SplashScreen
import com.github.yjnnev.taskmate.ui.theme.NoteAppTheme
import android.graphics.Color as AndroidColor // 2. Add this alias for clarity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 3. Force dark system bars with transparent backgrounds
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(AndroidColor.TRANSPARENT)
        )

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            NoteAppTheme {
                var showSplash by remember { mutableStateOf(true) }

                if (showSplash) {
                    SplashScreen(onNavigateToLogin = { showSplash = false })
                } else {
                    HomeScreen()
                }
            }
        }
    }
}