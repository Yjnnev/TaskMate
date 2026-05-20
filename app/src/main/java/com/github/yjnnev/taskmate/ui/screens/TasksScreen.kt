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
import com.github.yjnnev.taskmate.ui.components.TaskItem
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

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (tasks.isEmpty()) {
            EmptyState(
                title = "No Tasks Yet",
                subtitle = "You’re all caught up!",
                iconRes = R.drawable.ic_checkmark,
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
    }
}