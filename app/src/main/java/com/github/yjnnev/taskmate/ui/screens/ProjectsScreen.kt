package com.github.yjnnev.taskmate.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.yjnnev.taskmate.classes.Project
import com.github.yjnnev.taskmate.classes.User
import com.github.yjnnev.taskmate.ui.dialogs.CreateProjectDialog
import com.github.yjnnev.taskmate.ui.components.EmptyState
import com.github.yjnnev.taskmate.ui.components.ProjectCard
import com.github.yjnnev.taskmate.ui.components.ButtonRow
import com.github.yjnnev.taskmate.ui.components.ProjectsList
import com.github.yjnnev.taskmate.ui.dialogs.ProjectDetailDialog
import com.github.yjnnev.taskmate.ui.dialogs.JoinProjectDialog
import com.github.yjnnev.taskmate.ui.viewmodel.TaskMateViewModel
import com.github.yjnnev.taskmate.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProjectsScreenContainer(
    viewModel: TaskMateViewModel = viewModel()
) {
    var showCreateDialog by remember { mutableStateOf(false) }
    var showJoinDialog by remember { mutableStateOf(false) }
    var selectedProject by remember { mutableStateOf<Project?>(null) }

    val currentUser by viewModel.currentUser.collectAsState()
    val activeProjects by viewModel.projects.collectAsState()
    val isSyncing by viewModel.isSyncing.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Button row - always visible with equal sizes
        ButtonRow(
            onSync = { if (!isSyncing) viewModel.seedData() },
            onNewProject = { showCreateDialog = true },
            onJoinProject = { showJoinDialog = true }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isSyncing) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else if (activeProjects.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                EmptyState(
                    title = "No Active Projects",
                    subtitle = "Start by creating your first project to manage tasks.",
                    buttonText = "Create Project",
                    iconRes = R.drawable.ic_ghost,
                    onActionClick = { showCreateDialog = true }
                )
            }
        } else {
            ProjectsList(
                projects = activeProjects,
                onProjectClick = { project -> selectedProject = project }
            )
        }
    }

    // Dialogs
    if (showCreateDialog) {
        CreateProjectDialog(
            currentUser = currentUser,
            onDismiss = { showCreateDialog = false },
            onCreate = { project ->
                viewModel.createProject(project)
                showCreateDialog = false
            }
        )
    }

    if (showJoinDialog) {
        JoinProjectDialog(
            currentUser = currentUser,
            onDismiss = { showJoinDialog = false },
            onJoin = { code, callback ->
                viewModel.joinProject(code) { success ->
                    callback(success)
                    if (success) {
                        showJoinDialog = false
                    }
                }
            }
        )
    }

    if (selectedProject != null) {
        val user = currentUser
        if (user != null) {
            val projectTasks by viewModel.getProjectTasks(selectedProject!!.id).collectAsState(initial = emptyList())
            val projectMembers by viewModel.getProjectMembers(selectedProject!!.id).collectAsState(initial = emptyList())

            ProjectDetailDialog(
                project = selectedProject!!,
                currentUser = user,
                tasks = projectTasks,
                members = projectMembers,
                onDismiss = { selectedProject = null },
                onEditProject = { updatedProject ->
                    viewModel.updateProject(updatedProject)
                    selectedProject = null
                },
                onDeleteProject = { projectToDelete ->
                    viewModel.deleteProject(projectToDelete)
                    selectedProject = null
                },
                onInviteMembers = { project ->
                    // TODO: Implement invite members feature
                },
                onViewMembers = { project ->
                    // TODO: Implement view members feature
                },
                onAssignTask = { task ->
                    // TODO: Implement assign task feature
                },
                onTaskCreated = { newTask ->
                    viewModel.createTask(newTask)
                },
                onTaskStatusChange = { task, newStatus ->
                    viewModel.updateTaskStatus(task, newStatus)
                }
            )
        }
    }
}