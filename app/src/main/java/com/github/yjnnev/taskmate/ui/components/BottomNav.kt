package com.github.yjnnev.taskmate.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.yjnnev.taskmate.R // Replace with your actual package name

sealed class Screen(val route: String, val title: String, val iconRes: Int) {
    object Tasks : Screen("tasks", "Tasks", R.drawable.ic_ghost)
    object Reviews : Screen("reviews", "Reviews", R.drawable.ic_ghost)
    object Projects : Screen("projects", "Projects", R.drawable.ic_ghost)
}

@Composable
fun BottomNav(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(Screen.Tasks, Screen.Reviews, Screen.Projects)

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Gray
    ) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(screen.route) },
                label = { Text(text = screen.title) },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.iconRes),
                        contentDescription = screen.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF102A43),
                    selectedTextColor = Color(0xFF102A43),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color(0xFFE2E8F0) // Subtle highlight background for active item
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun PreviewBottomNav(){
    BottomNav(currentRoute = "", onNavigate = {})
}