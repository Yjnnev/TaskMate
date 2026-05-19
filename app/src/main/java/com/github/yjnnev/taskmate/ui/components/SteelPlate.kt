package com.github.yjnnev.taskmate.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import com.github.yjnnev.taskmate.ui.theme.*


@Composable
fun SteelPlate(
    title: String,
    baseColor: Color,
    darkColor: Color,
    modifier: Modifier = Modifier,
    isNailed: Boolean = true
) {
    // Each instance of SteelPlate manages its own private state
    var isDark by remember { mutableStateOf(false) }
    val backgroundColor = if (isDark) darkColor else baseColor

    Box(
        modifier = modifier
            .fillMaxSize() // Fill the space given by the parent (like weight)
            .background(backgroundColor)
            .padding(8.dp)
    ) {
        // Corner Nails
        if (isNailed){
            Circle(Modifier.align(Alignment.TopStart))
            Circle(Modifier.align(Alignment.TopEnd))
            Circle(Modifier.align(Alignment.BottomStart))
            Circle(Modifier.align(Alignment.BottomEnd))
        }

        // Toggle Button
        Button(
            onClick = { isDark = !isDark },
            modifier = Modifier.align(Alignment.Center),
            colors = ButtonDefaults.buttonColors(containerColor = Gray)
        ) {
            Text(text = title, color = White)
        }
    }
}
