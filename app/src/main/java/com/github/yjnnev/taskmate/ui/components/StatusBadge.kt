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
import com.github.yjnnev.taskmate.classes.TaskStatus

@Composable
fun StatusBadge(status: TaskStatus) {
    val (text, color) = when (status) {
        TaskStatus.TODO -> "To Do" to Color(0xFF6B7280)
        TaskStatus.IN_PROGRESS -> "In Progress" to Color(0xFFF59E0B)
        TaskStatus.COMPLETED -> "Done" to Color(0xFF4CAF50)
    }

    Surface(
        shape = RoundedCornerShape(6.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            color = color,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
