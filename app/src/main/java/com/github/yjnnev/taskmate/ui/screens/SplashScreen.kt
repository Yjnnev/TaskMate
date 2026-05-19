package com.github.yjnnev.taskmate.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.github.yjnnev.taskmate.R
import com.github.yjnnev.taskmate.ui.theme.NavyDark
import com.github.yjnnev.taskmate.ui.theme.TaskMatePurple

@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit) {
    // This value will start at 0f and animate to 1f
    val scale = remember { Animatable(0f) }

    // LaunchedEffect runs as soon as the composable enters the screen
    LaunchedEffect(key1 = true) {
        // 1. Trigger the animation
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy, // Gives it a nice little pop
                stiffness = Spring.StiffnessLow
            )
        )

        // 2. Keep the logo on screen for 1.2 seconds after animating
        delay(1200L)

        // 3. Trigger the navigation callback
        onNavigateToLogin()
    }

    // UI Layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            // Apply the animated scale and alpha (fade-in) to the whole column
            modifier = Modifier
                .scale(scale.value)
                .alpha(scale.value)
        ) {
            Surface(
                modifier = Modifier.size(80.dp), // Scaled up slightly for the splash screen
                shape = RoundedCornerShape(20.dp),
                color = TaskMatePurple
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_taskmate),
                    contentDescription = "TaskMate Logo",
                    modifier = Modifier.padding(0.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "TaskMate",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    // We pass an empty lambda {} because we don't need real navigation in a preview
    SplashScreen(onNavigateToLogin = {})
}