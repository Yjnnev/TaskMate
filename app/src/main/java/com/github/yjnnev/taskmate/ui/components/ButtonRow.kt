package com.github.yjnnev.taskmate.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonRow(
    onSync: () -> Unit,
    onNewProject: () -> Unit,
    onJoinProject: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onSync,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp),
            border = ButtonDefaults.outlinedButtonBorder,
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Sync",
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = onNewProject,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF102A43),
                disabledContainerColor = Color(0xFFE5E7EB)
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "New Project",
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = onJoinProject,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF102A43)),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Join Project",
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}
