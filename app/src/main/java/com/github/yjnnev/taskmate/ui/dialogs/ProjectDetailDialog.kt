package com.github.yjnnev.taskmate.ui.dialogs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.yjnnev.taskmate.classes.Project
import com.github.yjnnev.taskmate.classes.Task
import com.github.yjnnev.taskmate.classes.TaskStatus
import com.github.yjnnev.taskmate.classes.User
import com.github.yjnnev.taskmate.ui.components.DescriptionSection
import com.github.yjnnev.taskmate.ui.components.ProgressSection
import com.github.yjnnev.taskmate.ui.components.ProjectDetailHeader
import com.github.yjnnev.taskmate.ui.components.QuickActionsSection
import com.github.yjnnev.taskmate.ui.components.TaskItem

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
    onDeleteProject: (Project) -> Unit = {},
    onAssignTask: (Task) -> Unit = {},
    onTaskCreated: (Task) -> Unit = {},
    onTaskDeleted: (Task) -> Unit = {},
    onTaskStatusChange: (Task, TaskStatus) -> Unit = { _, _ -> },
    onLeaveProject: (Project) -> Unit = {},
    members: List<Pair<User, String>> = emptyList()
) {
    var showAllTasks by remember { mutableStateOf(false) }
    var showCreateTaskDialog by remember { mutableStateOf(false) }
    var showEditProjectDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var showMembersDialog by remember { mutableStateOf(false) }

    // Add local state to track tasks if needed
    var localTasks by remember(tasks) { mutableStateOf(tasks) }

    val isOwner = project.owner.email == currentUser.email
    val currentTotalTasks = localTasks.size
    val currentCompletedTasks = localTasks.count { it.status == TaskStatus.COMPLETED }

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
                    onEditProject = { showEditProjectDialog = true },
                    onDeleteProject = { showDeleteConfirmDialog = true }
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
                            isOwner = isOwner,
                            onInviteMembers = { onInviteMembers(project) },
                            onViewMembers = { showMembersDialog = true },
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
                                text = if (currentTotalTasks > 0) "Tasks ($currentCompletedTasks/$currentTotalTasks)" else "Tasks",
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
                    val displayTasks = if (showAllTasks) localTasks else localTasks.take(3)
                    if (displayTasks.isEmpty()) {
                        item {
                            Text(
                                text = "No tasks",
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
                                onAssignTask = { onAssignTask(task) },
                                onDeleteTask = {
                                    localTasks = localTasks.filter { it.id != task.id }
                                    onTaskDeleted(task)
                                }
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
                    // Update local state immediately
                    localTasks = localTasks + newTask
                    // Notify parent
                    onTaskCreated(newTask)
                    showCreateTaskDialog = false
                }
            )
        }

        if (showEditProjectDialog) {
            EditProjectDialog(
                project = project,
                onDismiss = { showEditProjectDialog = false },
                onConfirm = { updatedProject ->
                    onEditProject(updatedProject)
                    showEditProjectDialog = false
                }
            )
        }

        if (showDeleteConfirmDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmDialog = false },
                title = { Text("Delete Project") },
                text = { Text("Are you sure you want to delete '${project.title}'? This action cannot be undone.") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onDeleteProject(project)
                            showDeleteConfirmDialog = false
                        }
                    ) {
                        Text("Delete", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteConfirmDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        if (showMembersDialog) {
            MembersDialog(
                members = members,
                onDismiss = { showMembersDialog = false },
                onLeaveProject = {
                    showMembersDialog = false
                    if (isOwner && members.size <= 1) {
                        showDeleteConfirmDialog = true
                    } else {
                        onLeaveProject(project)
                        onDismiss()
                    }
                }
            )
        }
    }
}
