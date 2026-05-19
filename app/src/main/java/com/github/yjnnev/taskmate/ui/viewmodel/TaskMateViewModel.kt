package com.github.yjnnev.taskmate.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.yjnnev.taskmate.classes.Project
import com.github.yjnnev.taskmate.classes.Task
import com.github.yjnnev.taskmate.classes.User
import com.github.yjnnev.taskmate.data.UserManager
import com.github.yjnnev.taskmate.data.local.entity.ProjectEntity
import com.github.yjnnev.taskmate.data.local.entity.TaskEntity
import com.github.yjnnev.taskmate.data.repository.TaskMateRepository
import com.github.yjnnev.taskmate.di.AppModule
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskMateViewModel(
    private val repository: TaskMateRepository = AppModule.getRepository()
) : ViewModel() {

    val currentUser = UserManager.currentUser

    val projects: StateFlow<List<Project>> = currentUser
        .flatMapLatest { user ->
            if (user != null) {
                repository.observeUserProjects(user.id).map { entities ->
                    entities.map { it.toProject(user) }
                }
            } else {
                flowOf(emptyList())
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allTasks: StateFlow<List<Task>> = currentUser
        .flatMapLatest { user ->
            if (user != null) {
                repository.observeUserTasks(user.id).map { entities ->
                    entities.map { it.toTask() }
                }
            } else {
                flowOf(emptyList())
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getProjectTasks(projectId: String): Flow<List<Task>> {
        return repository.observeProjectTasks(projectId).map { entities ->
            entities.map { it.toTask() }
        }
    }

    fun createProject(project: Project) {
        viewModelScope.launch {
            repository.createProject(project.toEntity())
        }
    }

    fun createTask(task: Task) {
        viewModelScope.launch {
            repository.createTask(task.toEntity())
        }
    }

    fun updateTaskStatus(task: Task, newStatus: com.github.yjnnev.taskmate.classes.TaskStatus) {
        viewModelScope.launch {
            repository.updateTask(task.copy(status = newStatus, updatedAt = System.currentTimeMillis()).toEntity())
        }
    }

    // Mappers
    private fun ProjectEntity.toProject(owner: User): Project {
        return Project(
            id = id,
            title = title,
            description = description,
            category = category,
            owner = owner,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private fun Project.toEntity(): ProjectEntity {
        return ProjectEntity(
            id = id,
            title = title,
            description = description,
            category = category,
            ownerId = owner.id,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private fun TaskEntity.toTask(): Task {
        return Task(
            id = id,
            projectId = projectId,
            title = title,
            description = description,
            status = status,
            dueDate = dueDate,
            priority = priority,
            assignedToUserId = assignedToUserId,
            createdByUserId = createdByUserId,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private fun Task.toEntity(): TaskEntity {
        return TaskEntity(
            id = id,
            projectId = projectId,
            title = title,
            description = description,
            status = status,
            dueDate = dueDate,
            priority = priority,
            assignedToUserId = assignedToUserId,
            createdByUserId = createdByUserId,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
