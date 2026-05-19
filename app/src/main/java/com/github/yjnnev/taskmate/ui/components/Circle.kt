package com.github.yjnnev.taskmate.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.yjnnev.taskmate.ui.theme.White

@Composable
fun Circle(
    modifier: Modifier = Modifier,
    color: Color = White
){
    Box(
         modifier = modifier
             .size(10.dp)
             .background(color, shape = CircleShape)
    )
}
