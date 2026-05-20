package com.github.yjnnev.taskmate

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.github.yjnnev.taskmate.data.UserManager
import com.github.yjnnev.taskmate.ui.screens.LoginScreen
import com.github.yjnnev.taskmate.ui.screens.MainScreen
import com.github.yjnnev.taskmate.ui.screens.SplashScreen
import com.github.yjnnev.taskmate.ui.theme.NavyDark
import com.github.yjnnev.taskmate.ui.theme.TaskMateTheme
import com.github.yjnnev.taskmate.utils.NetworkUtils
import android.graphics.Color as AndroidColor

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.dark(NavyDark.toArgb()),
            statusBarStyle = SystemBarStyle.dark(NavyDark.toArgb())
        )

        setContent {
            TaskMateTheme {
                var showSplash by remember { mutableStateOf(true) }
                val currentUserId by UserManager.currentUserId.collectAsState()
                val context = LocalContext.current
                val isOnline = remember { NetworkUtils.isInternetAvailable(context) }

                if (showSplash) {
                    SplashScreen(onNavigateToLogin = { showSplash = false })
                } else {
                    // Skip LoginScreen if user is logged in OR if there's no internet
                    if (currentUserId != null || !isOnline) {
                        MainScreen()
                    } else {
                        LoginScreen()
                    }
                }
            }
        }
    }
}
