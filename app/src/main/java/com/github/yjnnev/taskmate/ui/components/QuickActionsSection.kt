package com.github.yjnnev.taskmate.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuickActionsSection(
    onInviteMembers: () -> Unit,
    onViewMembers: () -> Unit,
    onAddTask: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Members button
        OutlinedButton(
            onClick = onViewMembers,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF102A43)
            ),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Group,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Members",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Invite button
        OutlinedButton(
            onClick = onInviteMembers,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF102A43)
            ),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.PersonAdd,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Invite",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Add Task button
        Button(
            onClick = onAddTask,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF102A43)
            ),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Task",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
