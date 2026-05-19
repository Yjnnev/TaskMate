package com.github.yjnnev.taskmate.classes

data class Project(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String = "",
    val description: String = "",
    val category: ProjectCategory = ProjectCategory.WORK,
    val owner: User = User(),
    val memberCount: Int = 1,
    val completedTasks: Int = 0,
    val totalTasks: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)