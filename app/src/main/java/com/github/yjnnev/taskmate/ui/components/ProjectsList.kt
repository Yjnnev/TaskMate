package com.github.yjnnev.taskmate.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.yjnnev.taskmate.classes.Project

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
