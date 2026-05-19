package com.github.yjnnev.taskmate.ui.screens

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.yjnnev.taskmate.R
import com.github.yjnnev.taskmate.ui.components.BottomNav
import com.github.yjnnev.taskmate.ui.components.Header

@Composable
fun HomeScreen() {
    // Simple state tracking for bottom navigation simulation
    var currentRoute by remember { mutableStateOf("tasks") }

    Scaffold(
        topBar = { Header() },
        bottomBar = {
            BottomNav (
                currentRoute = currentRoute,
                onNavigate = { currentRoute = it }
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            EmptyStateContent()
        }
    }
}

@Composable
fun EmptyStateContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 32.dp)
    ) {
        // Main Empty State Icon
        Icon(
            painter = painterResource(id = R.drawable.ic_ghost),
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Title text
        Text(
            text = "No project selected",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle text
        Text(
            text = "Select a project to view and manage tasks",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // View Projects Action Button
        Button(
            onClick = { /* Action here */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF102A43) // Dark slate/blue layout button color
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_ghost),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "View Projects",
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun homeScreenPreview(){
    HomeScreen()
}