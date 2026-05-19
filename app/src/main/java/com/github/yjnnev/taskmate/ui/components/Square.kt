package com.github.yjnnev.taskmate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Square(
    color: Color,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color)
    )
}