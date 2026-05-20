package com.github.yjnnev.taskmate.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.yjnnev.taskmate.R

sealed class Screen(
    val route: String,
    val title: String,
    @DrawableRes val iconRes: Int? = null,
    val iconVector: ImageVector? = null
) {
    object Tasks : Screen("tasks", "Tasks", iconVector = Icons.Outlined.CheckCircle)
    object Projects : Screen("projects", "Projects", iconVector = Icons.Outlined.Folder)
    object Login : Screen("login", "Login")
    object SignUp : Screen("signup", "Sign Up")
    object Profile : Screen("profile", "Account", iconVector = Icons.Outlined.Person)
    object Theme : Screen("theme", "Theme", iconVector = Icons.Outlined.Palette)
    object Settings : Screen("settings", "Settings")

    companion object {
        val bottomNavItems get() = listOf(Tasks, Projects)
    }
}