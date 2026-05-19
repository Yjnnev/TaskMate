package com.github.yjnnev.taskmate.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.yjnnev.taskmate.R
import com.github.yjnnev.taskmate.ui.components.EmptyState
import com.github.yjnnev.taskmate.ui.viewmodel.TaskMateViewModel
import com.github.yjnnev.taskmate.ui.dialogs.CreateTaskDialog
import com.github.yjnnev.taskmate.classes.Task
import com.github.yjnnev.taskmate.classes.TaskStatus
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import java.time.format.DateTimeFormatter
import androidx.compose.material.icons.filled.DateRange
import com.github.yjnnev.taskmate.classes.PriorityLevel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    viewModel: TaskMateViewModel = viewModel()
) {
    val tasks by viewModel.allTasks.collectAsState()
    var showCreateTaskDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (tasks.isEmpty()) {
            EmptyState(
                title = "No Tasks Yet",
                subtitle = "Your daily tasks will appear here. Start by creating a new one!",
                buttonText = "Add Task",
                iconRes = R.drawable.ic_ghost,
                onActionClick = { showCreateTaskDialog = true }
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onStatusChange = { newStatus ->
                            viewModel.updateTaskStatus(task, newStatus)
                        }
                    )
                }
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = { showCreateTaskDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color(0xFF102A43),
            contentColor = Color.White
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Task")
        }
    }

    if (showCreateTaskDialog) {
        // Since it's a general task, we might need a way to select a project.
        // For now, let's assume we can create a task without a project or with a default "Inbox" project.
        // But the current CreateTaskDialog requires a projectId.
        // Let's use the first project if available, or show a message.
        val projects by viewModel.projects.collectAsState()
        
        if (projects.isNotEmpty()) {
            CreateTaskDialog(
                projectId = projects.first().id,
                onDismiss = { showCreateTaskDialog = false },
                onCreate = { newTask ->
                    viewModel.createTask(newTask)
                    showCreateTaskDialog = false
                }
            )
        } else {
            AlertDialog(
                onDismissRequest = { showCreateTaskDialog = false },
                title = { Text("No Projects Found") },
                text = { Text("Please create a project first before adding tasks.") },
                confirmButton = {
                    TextButton(onClick = { showCreateTaskDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun TaskItem(
    task: Task,
    onStatusChange: (TaskStatus) -> Unit
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

            StatusBadge(status = task.status)
        }
    }
}

@Composable
private fun PriorityBadge(priority: PriorityLevel) {
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

@Composable
private fun StatusBadge(status: TaskStatus) {
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
