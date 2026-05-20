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
import com.github.yjnnev.taskmate.ui.components.TaskCard
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
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import com.github.yjnnev.taskmate.classes.PriorityLevel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    viewModel: TaskMateViewModel = viewModel()
) {
    val tasks by viewModel.allTasks.collectAsState()
    var showHidden by remember { mutableStateOf(false) }
    var sortDescending by remember { mutableStateOf(true) }
    
    val filteredTasks = if (showHidden) tasks else tasks.filter { !it.isHidden }
    val displayedTasks = if (sortDescending) {
        filteredTasks.sortedByDescending { it.priority.ordinal }
    } else {
        filteredTasks.sortedBy { it.priority.ordinal }
    }
    
    var showCreateTaskDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { showHidden = !showHidden }) {
                Icon(
                    imageVector = if (showHidden) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (showHidden) "Hide hidden tasks" else "Show hidden tasks",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = { sortDescending = !sortDescending }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Sort,
                    contentDescription = "Sort by priority",
                    tint = if (sortDescending) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (displayedTasks.isEmpty()) {
                EmptyState(
                    title = if (showHidden) "No Tasks Yet" else "No Visible Tasks",
                    subtitle = if (showHidden) "You haven't created any tasks yet." else "You’re all caught up!",
                    iconRes = R.drawable.ic_checkmark,
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(displayedTasks) { task ->
                        TaskCard(
                            task = task,
                            canChangeStatus = true,
                            onStatusChange = { newStatus ->
                                viewModel.updateTaskStatus(task, newStatus)
                            },
                            onLongClick = {
                                viewModel.toggleTaskVisibility(task)
                            }
                        )
                    }
                }
            }
        }
    }
}