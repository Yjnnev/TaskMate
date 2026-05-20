package com.github.yjnnev.taskmate.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.github.yjnnev.taskmate.classes.*
import com.github.yjnnev.taskmate.data.local.entity.ProjectEntity
import com.github.yjnnev.taskmate.data.local.entity.TaskEntity
import com.github.yjnnev.taskmate.data.local.entity.UserEntity
import com.github.yjnnev.taskmate.data.repository.TaskMateRepository
import java.time.LocalDate

object SampleData {
    val users = listOf(
        User(
            id = "user_1",
            name = "Axel Bro",
            email = "axel@example.com",
            password = "password",
            username = "Axel",
            authProvider = AuthProvider.EMAIL
        ),
        User(
            id = "user_2",
            name = "CJ Magdael",
            email = "cjdost@example.com",
            password = "password",
            username = "CJ Dost",
            authProvider = AuthProvider.GOOGLE
        ),
        User(
            id = "user_3",
            name = "Bob Smith",
            email = "bob@example.com",
            password = "password",
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

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun seedDatabase(repository: TaskMateRepository) {
        // Create users
        users.forEach { user ->
            repository.createOrUpdateUser(
                UserEntity(
                    id = user.id,
                    name = user.name,
                    email = user.email,
                    password = user.password,
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

        // Create sample tasks
        val sampleTasks = listOf(
            Task(
                id = "task_1",
                projectId = "proj_1",
                title = "Design UI Mockups",
                description = "Create high-fidelity mockups for the main screens.",
                status = TaskStatus.COMPLETED,
                priority = PriorityLevel.HIGH,
                assignedToUserId = "user_1",
                createdByUserId = "user_1",
                dueDate = LocalDate.now().plusDays(2)
            ),
            Task(
                id = "task_2",
                projectId = "proj_1",
                title = "Implement Room Database",
                description = "Set up entities, DAOs, and the database class.",
                status = TaskStatus.TODO,
                priority = PriorityLevel.HIGH,
                assignedToUserId = "user_1",
                createdByUserId = "user_1",
                dueDate = LocalDate.now().plusDays(5)
            ),
            Task(
                id = "task_3",
                projectId = "proj_1",
                title = "User Authentication",
                description = "Implement login and sign up with Firebase or similar.",
                status = TaskStatus.TODO,
                priority = PriorityLevel.MEDIUM,
                assignedToUserId = "user_2",
                createdByUserId = "user_1",
                dueDate = LocalDate.now().plusDays(7)
            ),
            Task(
                id = "task_4",
                projectId = "proj_2",
                title = "Buy Groceries",
                description = "Milk, eggs, bread, and fruits.",
                status = TaskStatus.TODO,
                priority = PriorityLevel.LOW,
                assignedToUserId = "user_2",
                createdByUserId = "user_2",
                dueDate = LocalDate.now()
            )
        )

        sampleTasks.forEach { task ->
            repository.createTask(
                TaskEntity(
                    id = task.id,
                    projectId = task.projectId,
                    title = task.title,
                    description = task.description,
                    status = task.status,
                    dueDate = task.dueDate,
                    priority = task.priority,
                    assignedToUserId = task.assignedToUserId,
                    createdByUserId = task.createdByUserId,
                    createdAt = task.createdAt,
                    updatedAt = task.updatedAt
                )
            )
        }
    }
}
