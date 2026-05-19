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
import com.github.yjnnev.taskmate.ui.dialogs.ProjectDetailDialog
import com.github.yjnnev.taskmate.ui.viewmodel.TaskMateViewModel
import com.github.yjnnev.taskmate.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProjectsScreenContainer(
    viewModel: TaskMateViewModel = viewModel()
) {
    var showCreateDialog by remember { mutableStateOf(false) }
    var selectedProject by remember { mutableStateOf<Project?>(null) }

    val currentUser by viewModel.currentUser.collectAsState()
    val activeProjects by viewModel.projects.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Button row - always visible with equal sizes
        ButtonRow(
            onSync = { /* TODO: Sync */ },
            onNewProject = { showCreateDialog = true },
            onJoinProject = { /* TODO: Join */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Content - either empty state or projects list
        if (activeProjects.isEmpty()) {
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

    if (selectedProject != null) {
        val user = currentUser
        if (user != null) {
            val projectTasks by viewModel.getProjectTasks(selectedProject!!.id).collectAsState(initial = emptyList())

            ProjectDetailDialog(
                project = selectedProject!!,
                currentUser = user,
                tasks = projectTasks,
                onDismiss = { selectedProject = null },
                onEditProject = { project ->
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
            contentPadding = PaddingValues(0.dp)  // Remove default padding
        ) {
            Text(
                text = "Sync",
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
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
            contentPadding = PaddingValues(0.dp)  // Remove default padding
        ) {
            Text(
                text = "New Project",
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Button(
            onClick = onJoinProject,
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF102A43)),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(0.dp)  // Remove default padding
        ) {
            Text(
                text = "Join Project",
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
fun ProjectsList(
    projects: List<Project>,
    onProjectClick: (Project) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = 8.dp,
            bottom = 32.dp
        )
    ) {
        items(projects) { project ->
            ProjectCard(
                project = project,
                onClick = { onProjectClick(project) }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = false)
@Composable
fun ProjectsScreenPreview() {
    ProjectsScreenContainer()
}