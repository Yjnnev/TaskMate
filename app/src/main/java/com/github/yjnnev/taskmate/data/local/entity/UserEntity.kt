package com.github.yjnnev.taskmate.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.yjnnev.taskmate.classes.AuthProvider

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val username: String,
    val profilePictureUrl: String?,
    val authProvider: AuthProvider,
    val createdAt: Long,
    val lastLoginAt: Long
)