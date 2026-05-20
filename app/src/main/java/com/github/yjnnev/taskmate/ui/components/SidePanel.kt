package com.github.yjnnev.taskmate.ui.components

import android.widget.Toast
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.github.yjnnev.taskmate.classes.User
import com.github.yjnnev.taskmate.data.UserManager
import com.github.yjnnev.taskmate.ui.navigation.Screen

@Composable
fun SidePanel(
    currentUser: User,
    onDismiss: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current

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
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(bottom = 72.dp) // Match BottomNav height to prevent overlap
                .fillMaxHeight()
                .fillMaxWidth(0.85f)
                .align(Alignment.CenterEnd)
                .clickable { /* Prevent click-through */ },
            color = Color.White,
            shadowElevation = 24.dp,
            tonalElevation = 4.dp
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
                UserProfileSection(
                    currentUser = currentUser,
                    onClick = {
                        onNavigate(Screen.Profile.route)
                        onDismiss()
                    }
                )

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
                            onClick = {
                                onNavigate(Screen.Theme.route)
                                onDismiss()
                            }
                        )
                    }

                    item {
                        SidePanelMenuItem(
                            icon = Icons.Outlined.Person,
                            title = "Account",
                            subtitle = "Manage your profile",
                            onClick = {
                                onNavigate(Screen.Profile.route)
                                onDismiss()
                            }
                        )
                    }
                    item {
                        SidePanelMenuItem(
                            icon = Icons.Outlined.Settings,
                            title = "Settings",
                            subtitle = "App preferences",
                            onClick = {
                                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }

                    item {
                        SidePanelMenuItem(
                            icon = Icons.Outlined.SwapHoriz,
                            title = "Switch Account",
                            subtitle = "Use a different account",
                            onClick = {
                                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
                            }
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