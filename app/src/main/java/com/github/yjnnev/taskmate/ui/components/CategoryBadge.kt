package com.github.yjnnev.taskmate.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.yjnnev.taskmate.classes.ProjectCategory


@Composable
fun CategoryBadge(
    category: ProjectCategory,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = category.color.copy(alpha = 0.1f),
        modifier = modifier
    ) {
        Text(
            text = category.displayName,
            fontSize = 10.sp,
            color = category.color,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            modifier = textModifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}