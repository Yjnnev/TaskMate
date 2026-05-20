package com.github.yjnnev.taskmate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.yjnnev.taskmate.classes.ProjectCategory

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val category: ProjectCategory,
    val code: String,
    val ownerId: String,
    val createdAt: Long,
    val updatedAt: Long
)