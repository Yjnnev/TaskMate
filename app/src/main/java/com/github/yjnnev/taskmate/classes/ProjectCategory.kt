package com.github.yjnnev.taskmate.classes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class ProjectCategory(
    val displayName: String,
    val icon: ImageVector,
    val color: Color
) {
    WORK(
        displayName = "Work",
        icon = Icons.Default.Business,
        color = Color(0xFF1E88E5)
    ),
    PERSONAL(
        displayName = "Personal",
        icon = Icons.Default.Person,
        color = Color(0xFF7C4DFF)
    ),
    EDUCATION(
        displayName = "Education",
        icon = Icons.Default.School,
        color = Color(0xFFFF6B6B)
    ),
    HEALTH(
        displayName = "Health",
        icon = Icons.Default.Favorite,
        color = Color(0xFF4CAF50)
    ),
    FINANCE(
        displayName = "Finance",
        icon = Icons.Default.AccountBalance,
        color = Color(0xFFFFA726)
    ),
    SOCIAL(
        displayName = "Social",
        icon = Icons.Default.People,
        color = Color(0xFFEC407A)
    ),
    OTHER(
        displayName = "Other",
        icon = Icons.Default.MoreHoriz,
        color = Color(0xFF78909C)
    )
}