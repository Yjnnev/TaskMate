package com.github.yjnnev.taskmate.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.yjnnev.taskmate.classes.PriorityLevel

@Composable
fun PriorityBadge(priority: PriorityLevel) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = Color(priority.color).copy(alpha = 0.1f)
    ) {
        Text(
            text = priority.displayName,
            fontSize = 10.sp,
            color = Color(priority.color),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}
