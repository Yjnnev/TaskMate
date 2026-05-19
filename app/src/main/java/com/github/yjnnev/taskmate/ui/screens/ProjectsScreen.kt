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
import com.github.yjnnev.taskmate.classes.Project
import com.github.yjnnev.taskmate.classes.User
import com.github.yjnnev.taskmate.ui.dialogs.CreateProjectDialog
import com.github.yjnnev.taskmate.ui.components.EmptyState
import com.github.yjnnev.taskmate.ui.components.ProjectCard
import com.github.yjnnev.taskmate.ui.dialogs.ProjectDetailDialog
import com.github.yjnnev.taskmate.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProjectsScreenContainer() {
    var showCreateDialog by remember { mutableStateOf(false) }
    var selectedProject by remember { mutableStateOf<Project?>(null) }

    val currentUser = remember {
        User(name = "Axel V", email = "axel.v@example.com")
    }

    var activeProjects by remember {
        mutableStateOf(emptyList<Project>())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Button row - always visible
        ButtonRow(
            onSync = { /* TODO: Sync */ },
            onNewProject = { showCreateDialog = true },
            onJoinProject = { /* TODO: Join */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Content - either empty state or projects list
        if (activeProjects.isEmpty()) {
            EmptyState(
                title = "No Active Projects",
                subtitle = "Start by creating your first project to manage tasks.",
                buttonText = "Create Project",
                iconRes = R.drawable.ic_ghost,
                onActionClick = { showCreateDialog = true }
            )
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
                activeProjects = activeProjects + project
                showCreateDialog = false
            }
        )
    }

    if (selectedProject != null) {
        ProjectDetailDialog(
            project = selectedProject!!,
            currentUser = currentUser,
            onDismiss = { selectedProject = null },
            onEditProject = { project ->
                selectedProject = null
            },
            onInviteMembers = { project ->
                // TODO: Implement invite members feature
            },
            onTaskCreated = { newTask ->
                activeProjects = activeProjects.map { p ->
                    if (p.id == selectedProject?.id) {
                        p.copy(totalTasks = p.totalTasks + 1)
                    } else p
                }
            }
        )
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
        OutlinedButton(
            onClick = onSync,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Sync",
                color = Color.Black,
                fontSize = 13.sp
            )
        }

        Button(
            onClick = onNewProject,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF102A43)),
            modifier = Modifier.weight(1.2f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "New Project",
                color = Color.White,
                fontSize = 13.sp
            )
        }

        Button(
            onClick = onJoinProject,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF102A43)),
            modifier = Modifier.weight(1.2f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Join Project",
                color = Color.White,
                fontSize = 13.sp
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