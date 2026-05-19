package com.github.yjnnev.taskmate.ui.dialogs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.yjnnev.taskmate.classes.*
import com.github.yjnnev.taskmate.ui.components.CategoryBadge
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProjectDetailDialog(
    project: Project,
    currentUser: User,
    tasks: List<Task>,
    onDismiss: () -> Unit,
    onEditProject: (Project) -> Unit,
    onInviteMembers: (Project) -> Unit,
    onViewMembers: (Project) -> Unit = {},
    onAssignTask: (Task) -> Unit = {},
    onTaskCreated: (Task) -> Unit = {},
    onTaskStatusChange: (Task, TaskStatus) -> Unit = { _, _ -> }
) {
    var showAllTasks by remember { mutableStateOf(false) }
    var showCreateTaskDialog by remember { mutableStateOf(false) }

    val isOwner = project.owner.email == currentUser.email
    val currentTotalTasks = tasks.size
    val currentCompletedTasks = tasks.count { it.status == TaskStatus.COMPLETED }
    
    val isCompleted = currentTotalTasks > 0 && currentCompletedTasks == currentTotalTasks
    val progress = if (currentTotalTasks > 0)
        currentCompletedTasks.toFloat() / currentTotalTasks.toFloat() else 0f

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.9f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header
                ProjectDetailHeader(
                    project = project,
                    isOwner = isOwner,
                    onDismiss = onDismiss,
                    onEditProject = { onEditProject(project) }
                )

                // Scrollable Content
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Progress Section
                    item {
                        ProgressSection(
                            project = project.copy(
                                totalTasks = currentTotalTasks,
                                completedTasks = currentCompletedTasks
                            ),
                            progress = progress,
                            isCompleted = isCompleted
                        )
                    }

                    // Quick Actions
                    item {
                        QuickActionsSection(
                            onInviteMembers = { onInviteMembers(project) },
                            onViewMembers = { onViewMembers(project) },
                            onAddTask = { showCreateTaskDialog = true }
                        )
                    }

                    // Description
                    if (project.description.isNotBlank()) {
                        item {
                            DescriptionSection(description = project.description)
                        }
                    }

                    // Tasks Section Header
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Tasks ($currentCompletedTasks/$currentTotalTasks)",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A1A1A)
                            )
                            TextButton(onClick = { showAllTasks = !showAllTasks }) {
                                Text(
                                    text = if (showAllTasks) "Show Less" else "View All",
                                    color = Color(0xFF102A43),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    // Task List
                    val displayTasks = if (showAllTasks) tasks else tasks.take(3)
                    if (displayTasks.isEmpty()) {
                        item {
                            Text(
                                text = "No tasks yet. Tap 'Add Task' to start.",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    } else {
                        items(displayTasks) { task ->
                            TaskItem(
                                task = task,
                                isOwner = isOwner,
                                onStatusChange = { newStatus ->
                                    onTaskStatusChange(task, newStatus)
                                },
                                onAssignTask = { onAssignTask(task) }
                            )
                        }
                    }

                    // Bottom spacer
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }

        if (showCreateTaskDialog) {
            CreateTaskDialog(
                projectId = project.id,
                onDismiss = { showCreateTaskDialog = false },
                onCreate = { newTask ->
                    onTaskCreated(newTask)
                    showCreateTaskDialog = false
                }
            )
        }
    }
}

@Composable
private fun ProjectDetailHeader(
    project: Project,
    isOwner: Boolean,
    onDismiss: () -> Unit,
    onEditProject: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                project.category.color.copy(alpha = 0.05f)
            )
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Category Icon
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            project.category.color.copy(alpha = 0.15f),
                            RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = project.category.icon,
                        contentDescription = "Category",
                        modifier = Modifier.size(28.dp),
                        tint = project.category.color
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = project.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    CategoryBadge(category = project.category)
                }
            }

            // Action Buttons
            Row {
                if (isOwner) {
                    IconButton(
                        onClick = onEditProject,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Project",
                            tint = Color(0xFF102A43),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Gray,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Owner Info
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(project.category.color),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = project.owner.username.take(1).uppercase(),
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Owned by ${project.owner.username}",
                fontSize = 13.sp,
                color = Color(0xFF6B7280)
            )
        }
    }
}

@Composable
private fun ProgressSection(
    project: Project,
    progress: Float,
    isCompleted: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) Color(0xFFF0F9F0) else Color(0xFFF8FAFC)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Progress",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A1A)
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isCompleted) Color(0xFF4CAF50) else project.category.color
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = if (isCompleted) Color(0xFF4CAF50) else project.category.color,
                trackColor = Color(0xFFE5E7EB),
                strokeCap = StrokeCap.Round,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    label = "Tasks",
                    value = "${project.completedTasks}/${project.totalTasks}"
                )
                StatItem(
                    label = "Members",
                    value = "${project.memberCount}"
                )
                StatItem(
                    label = "Status",
                    value = if (isCompleted) "Complete" else "Active"
                )
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A1A)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color(0xFF6B7280)
        )
    }
}

@Composable
private fun QuickActionsSection(
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

@Composable
private fun DescriptionSection(description: String) {
    Column {
        Text(
            text = "Description",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1A1A1A)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            fontSize = 14.sp,
            color = Color(0xFF6B7280),
            lineHeight = 20.sp
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun TaskItem(
    task: Task,
    isOwner: Boolean,
    onStatusChange: (TaskStatus) -> Unit,
    onAssignTask: () -> Unit
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
            // Checkbox for ticking tasks
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
                    // Due Date
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

                    // Priority Badge
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
                // Status Badge
                StatusBadge(status = task.status)
            }
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