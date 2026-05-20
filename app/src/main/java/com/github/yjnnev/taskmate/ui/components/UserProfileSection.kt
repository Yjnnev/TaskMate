package com.github.yjnnev.taskmate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.github.yjnnev.taskmate.classes.AuthProvider
import com.github.yjnnev.taskmate.classes.User
import com.github.yjnnev.taskmate.data.UserManager

@Composable
fun UserProfileSection(currentUser: User) {
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
