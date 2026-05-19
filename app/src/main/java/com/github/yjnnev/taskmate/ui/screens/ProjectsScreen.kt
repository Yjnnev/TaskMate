package com.github.yjnnev.taskmate.ui.screens

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
import com.github.yjnnev.taskmate.classes.ProjectCategory
import com.github.yjnnev.taskmate.classes.User
import com.github.yjnnev.taskmate.ui.dialogs.CreateProjectDialog
import com.github.yjnnev.taskmate.ui.components.EmptyState
import com.github.yjnnev.taskmate.ui.components.ProjectCard
import com.github.yjnnev.taskmate.ui.dialogs.ProjectDetailDialog
import com.github.yjnnev.taskmate.R

@Composable
fun ProjectsScreenContainer() {
    var showCreateDialog by remember { mutableStateOf(false) }
    var selectedProject by remember { mutableStateOf<Project?>(null) }

    val currentUser = remember {
        User(name = "Axel V", email = "axel.v@example.com")
    }

    var activeProjects by remember {
        mutableStateOf(
            listOf(
                Project(
                    title = "Database Systems",
                    description = "Managing complex database structures",
                    category = ProjectCategory.WORK,
                    owner = currentUser,
                    memberCount = 3,
                    completedTasks = 2,
                    totalTasks = 5
                ),
                Project(
                    title = "Mobile Development",
                    description = "Android app for task management",
                    category = ProjectCategory.PERSONAL,
                    owner = currentUser,
                    memberCount = 2,
                    completedTasks = 2,
                    totalTasks = 2
                )
            )
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (activeProjects.isEmpty()) {
            EmptyState(
                title = "No Active Projects",
                subtitle = "Select or join a project to view and manage tasks",
                buttonText = "View Projects",
                iconRes = R.drawable.ic_ghost,
                onActionClick = { /* TODO: Navigate to view projects */ }
            )
        } else {
            ProjectsContent(
                projects = activeProjects,
                onNewProject = { showCreateDialog = true },
                onProjectClick = { project -> selectedProject = project }
            )
        }

        // Show create dialog when needed
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

        // Show project detail dialog when a project is selected
        if (selectedProject != null) {
            ProjectDetailDialog(
                project = selectedProject!!,
                currentUser = currentUser,
                onDismiss = { selectedProject = null },
                onEditProject = { project ->
                    // TODO: Implement edit project functionality
                    // For now, you can show a snackbar or navigate to edit screen
                    selectedProject = null
                },
                onInviteMembers = { project ->
                    // TODO: Implement invite members feature
                    // Show invite dialog or navigate to invite screen
                }
            )
        }
    }
}

@Composable
fun ProjectsContent(
    projects: List<Project>,
    onNewProject: () -> Unit,
    onProjectClick: (Project) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Top Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { /* TODO: Sync */ },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "Sync", color = Color.Black, fontSize = 13.sp)
            }
            Button(
                onClick = onNewProject,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF102A43)),
                modifier = Modifier.weight(1.2f),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "New Project", color = Color.White, fontSize = 13.sp)
            }
            Button(
                onClick = { /* TODO: Join */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF102A43)),
                modifier = Modifier.weight(1.2f),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "Join Project", color = Color.White, fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize(),
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
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun ProjectsScreenPreview() {
    ProjectsScreenContainer()
}