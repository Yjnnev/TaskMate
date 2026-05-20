package com.github.yjnnev.taskmate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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
                if (currentUser?.profilePictureUrl != null) {
                    AsyncImage(
                        model = currentUser?.profilePictureUrl,
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
                            text = UserManager.getInitials(currentUser?.name ?: ""),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = currentUser?.username?.ifEmpty { "User" } ?: "Guest",
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
    if (showSidePanel && currentUser != null) {
        SidePanel(
            currentUser = currentUser!!,
            onDismiss = { showSidePanel = false }
        )
    }
}
