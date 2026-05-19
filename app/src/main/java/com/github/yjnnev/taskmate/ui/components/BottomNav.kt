package com.github.yjnnev.taskmate.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.yjnnev.taskmate.R
import com.github.yjnnev.taskmate.ui.navigation.Screen
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.windowInsetsBottomHeight

@Composable
fun BottomNav(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = Screen.bottomNavItems

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {

        // Background extension into system nav area
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsBottomHeight(WindowInsets.navigationBars)
                .align(Alignment.BottomCenter)
                .background(Color.White)
        )

        // Actual floating nav bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            shadowElevation = 8.dp,
            color = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            NavigationBar(
                containerColor = Color.Transparent,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
            ) {
                items.forEach { screen ->
                    val isSelected = currentRoute == screen.route

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { onNavigate(screen.route) },
                        icon = {

                            Box(
                                modifier = Modifier
                                    .size(if (isSelected) 36.dp else 32.dp)
                                    .animateContentSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {

                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF102A43).copy(alpha = 0.1f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(
                                                id = screen.iconRes ?: R.drawable.ic_ghost
                                            ),
                                            contentDescription = screen.title,
                                            modifier = Modifier.size(20.dp),
                                            tint = Color(0xFF102A43)
                                        )
                                    }
                                } else {

                                    Icon(
                                        painter = painterResource(
                                            id = screen.iconRes ?: R.drawable.ic_ghost
                                        ),
                                        contentDescription = screen.title,
                                        modifier = Modifier.size(22.dp),
                                        tint = Color(0xFF9CA3AF)
                                    )
                                }
                            }
                        },
                        label = {
                            Text(
                                text = screen.title,
                                fontSize = if (isSelected) 12.sp else 11.sp,
                                fontWeight = if (isSelected)
                                    FontWeight.SemiBold
                                else
                                    FontWeight.Normal,
                                color = if (isSelected)
                                    Color(0xFF102A43)
                                else
                                    Color(0xFF9CA3AF)
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Transparent,
                            selectedTextColor = Color(0xFF102A43),
                            unselectedIconColor = Color.Transparent,
                            unselectedTextColor = Color(0xFF9CA3AF),
                            indicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun PreviewBottomNav() {
    // Preview showing different states
    Column {
        BottomNav(currentRoute = "tasks", onNavigate = {})
    }
}