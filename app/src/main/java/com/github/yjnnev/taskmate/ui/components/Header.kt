package com.github.yjnnev.taskmate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.github.yjnnev.taskmate.classes.AuthProvider
import com.github.yjnnev.taskmate.classes.User
import com.github.yjnnev.taskmate.data.UserManager

@Composable
fun Header() {
    val currentUser by UserManager.currentUser.collectAsState()
    var showSidePanel by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // App Title & Subtitle
        Column {
            Text(
                text = "TaskMate",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF102A43)
            )
            Text(
                text = "Academic Collaboration",
                fontSize = 13.sp,
                color = Color(0xFF64748B),
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )
        }

        // Profile / User Chip - Modern version
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .clickable { showSidePanel = true },
            shape = RoundedCornerShape(50),
            color = Color(0xFFF1F5F9),
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                if (currentUser.profilePictureUrl != null) {
                    AsyncImage(
                        model = currentUser.profilePictureUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF1E88E5),
                                        Color(0xFF1565C0)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = UserManager.getInitials(currentUser.name),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = currentUser.username.ifEmpty { "User" },
                    color = Color(0xFF1E293B),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.widthIn(max = 120.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Menu",
                    tint = Color(0xFF64748B),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }

    // Side Panel
    if (showSidePanel) {
        SidePanel(
            currentUser = currentUser,
            onDismiss = { showSidePanel = false }
        )
    }
}

@Composable
private fun SidePanel(
    currentUser: User,
    onDismiss: () -> Unit
) {
    // Semi-transparent background overlay
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { onDismiss() }
            .zIndex(1f)
    ) {
        // Panel content
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterEnd)
                .clickable { /* Prevent click-through */ },
            shape = RoundedCornerShape(topStart = 32.dp, bottomStart = 32.dp),
            color = Color.White,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF64748B),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // User Profile Section
                UserProfileSection(currentUser = currentUser)

                Spacer(modifier = Modifier.height(32.dp))

                // Menu Items
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        SidePanelMenuItem(
                            icon = Icons.Outlined.Palette,
                            title = "Theme",
                            subtitle = "Customize your experience",
                            onClick = { /* TODO: Theme settings */ }
                        )
                    }

                    item {
                        SidePanelMenuItem(
                            icon = Icons.Outlined.Person,
                            title = "Account",
                            subtitle = "Manage your profile",
                            onClick = { /* TODO: Account settings */ }
                        )
                    }

                    item {
                        SidePanelMenuItem(
                            icon = Icons.Outlined.Settings,
                            title = "Settings",
                            subtitle = "App preferences",
                            onClick = { /* TODO: Settings */ }
                        )
                    }

                    item {
                        SidePanelMenuItem(
                            icon = Icons.Outlined.SwapHoriz,
                            title = "Switch Account",
                            subtitle = "Use a different account",
                            onClick = { /* TODO: Switch account */ }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    item {
                        SidePanelMenuItem(
                            icon = Icons.AutoMirrored.Outlined.Logout,
                            title = "Log Out",
                            subtitle = "Sign out of your account",
                            onClick = {
                                UserManager.signOut()
                                onDismiss()
                            },
                            isDestructive = true
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UserProfileSection(currentUser: User) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFF8FAFC)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Large Avatar
            if (currentUser.profilePictureUrl != null) {
                AsyncImage(
                    model = currentUser.profilePictureUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF1E88E5),
                                    Color(0xFF1565C0)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = UserManager.getInitials(currentUser.name),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = currentUser.name.ifEmpty { "User" },
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = currentUser.email.ifEmpty { "No email" },
                fontSize = 14.sp,
                color = Color(0xFF64748B)
            )

            // Auth provider badge
            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = when (currentUser.authProvider) {
                    AuthProvider.GOOGLE -> Color(0xFF4285F4).copy(alpha = 0.1f)
                    AuthProvider.EMAIL -> Color(0xFF102A43).copy(alpha = 0.1f)
                }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when (currentUser.authProvider) {
                            AuthProvider.GOOGLE -> Icons.Outlined.Email // Replace with Google icon if available
                            AuthProvider.EMAIL -> Icons.Outlined.Email
                        },
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = when (currentUser.authProvider) {
                            AuthProvider.GOOGLE -> Color(0xFF4285F4)
                            AuthProvider.EMAIL -> Color(0xFF102A43)
                        }
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = when (currentUser.authProvider) {
                            AuthProvider.GOOGLE -> "Google Account"
                            AuthProvider.EMAIL -> "Email Account"
                        },
                        fontSize = 12.sp,
                        color = when (currentUser.authProvider) {
                            AuthProvider.GOOGLE -> Color(0xFF4285F4)
                            AuthProvider.EMAIL -> Color(0xFF102A43)
                        },
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun SidePanelMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = if (isDestructive) Color(0xFFFEF2F2) else Color(0xFFF8FAFC)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (isDestructive) Color(0xFFFEE2E2)
                        else Color.White
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = if (isDestructive) Color(0xFFEF4444) else Color(0xFF102A43)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDestructive) Color(0xFFEF4444) else Color(0xFF1E293B)
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = if (isDestructive) Color(0xFFFCA5A5) else Color(0xFF94A3B8),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = if (isDestructive) Color(0xFFFCA5A5) else Color(0xFFCBD5E1)
            )
        }
    }
}