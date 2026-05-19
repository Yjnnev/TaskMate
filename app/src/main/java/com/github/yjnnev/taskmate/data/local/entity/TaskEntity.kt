package com.github.yjnnev.taskmate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.yjnnev.taskmate.classes.PriorityLevel
import com.github.yjnnev.taskmate.classes.TaskStatus
import java.time.LocalDate

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val projectId: String,
    val title: String,
    val description: String,
    val status: TaskStatus,
    val dueDate: LocalDate?,
    val priority: PriorityLevel,
    val assignedToUserId: String?,
    val createdByUserId: String,
    val createdAt: Long,
    val updatedAt: Long
)