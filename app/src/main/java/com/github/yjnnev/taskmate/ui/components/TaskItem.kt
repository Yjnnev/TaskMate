package com.github.yjnnev.taskmate.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.yjnnev.taskmate.classes.Task
import com.github.yjnnev.taskmate.classes.TaskStatus
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskItem(
    task: Task,
    isOwner: Boolean = false,
    onStatusChange: (TaskStatus) -> Unit,
    onAssignTask: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8FAFC)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.status == TaskStatus.COMPLETED,
                onCheckedChange = { checked ->
                    onStatusChange(if (checked) TaskStatus.COMPLETED else TaskStatus.TODO)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF4CAF50),
                    uncheckedColor = Color(0xFF9CA3AF)
                ),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (task.status == TaskStatus.COMPLETED) Color.Gray else Color(0xFF1A1A1A),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = if (task.status == TaskStatus.COMPLETED) TextDecoration.LineThrough else null
                )

                if (task.description.isNotBlank()) {
                    Text(
                        text = task.description,
                        fontSize = 12.sp,
                        color = Color(0xFF6B7280),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (task.dueDate != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null,
                                modifier = Modifier.size(12.dp),
                                tint = Color(0xFF9CA3AF)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = task.dueDate.format(DateTimeFormatter.ofPattern("MMM dd")),
                                fontSize = 11.sp,
                                color = Color(0xFF6B7280)
                            )
                        }
                    }
                    PriorityBadge(priority = task.priority)
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isOwner) {
                    IconButton(
                        onClick = onAssignTask,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AssignmentInd,
                            contentDescription = "Assign Task",
                            tint = Color(0xFF102A43),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }
                StatusBadge(status = task.status)
            }
        }
    }
}
