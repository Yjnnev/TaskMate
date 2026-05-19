package com.github.yjnnev.taskmate.classes

data class Project(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String = "",
    val description: String = "",
    val category: ProjectCategory = ProjectCategory.WORK,
    val ownerId: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)