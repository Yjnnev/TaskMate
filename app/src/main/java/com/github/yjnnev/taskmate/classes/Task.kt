package com.github.yjnnev.taskmate.classes

import java.time.LocalDate

data class Task(
    val id: String = java.util.UUID.randomUUID().toString(),
    val projectId: String = "",
    val title: String = "",
    val description: String = "",
    val status: TaskStatus = TaskStatus.TODO,
    val dueDate: LocalDate? = null,
    val priority: PriorityLevel = PriorityLevel.MEDIUM,
    val assignedToUserId: String? = null, // Can be null for unassigned tasks
    val createdByUserId: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isHidden: Boolean = false
)