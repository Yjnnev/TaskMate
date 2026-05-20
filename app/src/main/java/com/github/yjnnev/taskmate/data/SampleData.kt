package com.github.yjnnev.taskmate.data

import com.github.yjnnev.taskmate.classes.Project
import com.github.yjnnev.taskmate.classes.ProjectCategory
import com.github.yjnnev.taskmate.classes.User
import com.github.yjnnev.taskmate.classes.AuthProvider
import com.github.yjnnev.taskmate.data.local.entity.ProjectEntity
import com.github.yjnnev.taskmate.data.local.entity.UserEntity
import com.github.yjnnev.taskmate.data.repository.TaskMateRepository

object SampleData {
    val users = listOf(
        User(
            id = "user_1",
            name = "Axel Bro",
            email = "axel@example.com",
            username = "Axel",
            authProvider = AuthProvider.EMAIL
        ),
        User(
            id = "user_2",
            name = "CJ Magdael",
            email = "cjdost@example.com",
            username = "CJ Dost",
            authProvider = AuthProvider.GOOGLE
        ),
        User(
            id = "user_3",
            name = "Bob Smith",
            email = "bob@example.com",
            username = "bobsmith",
            authProvider = AuthProvider.EMAIL
        )
    )

    val projects = listOf(
        Project(
            id = "proj_1",
            title = "TaskMate",
            description = "Building a task management app with Jetpack Compose and Room.",
            code = "APP1234",
            category = ProjectCategory.WORK,
            owner = users[0]
        ),
        Project(
            id = "proj_2",
            title = "DOST Project",
            description = "Weekly shopping items and meal planning.",
            code = "DOST123",
            category = ProjectCategory.PERSONAL,
            owner = users[1]
        ),
        Project(
            id = "proj_3",
            title = "Calculus II Study",
            description = "Preparation for the upcoming final exam.",
            code = "MATH456",
            category = ProjectCategory.EDUCATION,
            owner = users[2]
        ),
        Project(
            id = "proj_4",
            title = "Fitness Tracker",
            description = "Tracking daily workouts and calorie intake.",
            code = "FIT7890",
            category = ProjectCategory.HEALTH,
            owner = users[0]
        )
    )

    suspend fun seedDatabase(repository: TaskMateRepository) {
        // Create users
        users.forEach { user ->
            repository.createOrUpdateUser(
                UserEntity(
                    id = user.id,
                    name = user.name,
                    email = user.email,
                    username = user.username,
                    profilePictureUrl = user.profilePictureUrl,
                    authProvider = user.authProvider,
                    createdAt = System.currentTimeMillis(),
                    lastLoginAt = System.currentTimeMillis()
                )
            )
        }

        // Create projects
        projects.forEach { project ->
            repository.createProject(
                ProjectEntity(
                    id = project.id,
                    title = project.title,
                    description = project.description,
                    category = project.category,
                    code = project.code,
                    ownerId = project.owner.id,
                    createdAt = project.createdAt,
                    updatedAt = project.updatedAt
                )
            )
        }
    }
}
