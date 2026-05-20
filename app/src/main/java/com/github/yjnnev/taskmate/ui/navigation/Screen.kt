package com.github.yjnnev.taskmate.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.yjnnev.taskmate.R

sealed class Screen(
    val route: String,
    val title: String,
    @DrawableRes val iconRes: Int? = null,
    val iconVector: ImageVector? = null
) {
    object Tasks : Screen("tasks", "Tasks", iconRes = R.drawable.ic_ghost)
    object Projects : Screen("projects", "Projects", iconRes = R.drawable.ic_ghost)
    object Login : Screen("login", "Login")

    // Easy to add new screens:
    // object Settings : Screen("settings", "Settings", iconRes = R.drawable.ic_settings)
    // object Profile : Screen("profile", "Profile", iconRes = R.drawable.ic_profile)

    companion object {
        val bottomNavItems get() = listOf(Tasks, Projects)
    }
}