package com.github.yjnnev.taskmate.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.yjnnev.taskmate.classes.Project
import com.github.yjnnev.taskmate.classes.Task
import com.github.yjnnev.taskmate.classes.User
import com.github.yjnnev.taskmate.data.UserManager
import com.github.yjnnev.taskmate.data.SampleData
import com.github.yjnnev.taskmate.data.local.entity.ProjectEntity
import com.github.yjnnev.taskmate.data.local.entity.TaskEntity
import com.github.yjnnev.taskmate.data.local.entity.UserEntity
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

    fun getProjectMembers(projectId: String): Flow<List<Pair<User, String>>> {
        return repository.observeProjectMembers(projectId).flatMapLatest { members ->
            if (members.isEmpty()) return@flatMapLatest flowOf(emptyList())
            
            repository.observeAllUsers().map { allUsers ->
                members.mapNotNull { member ->
                    val userEntity = allUsers.find { it.id == member.userId }
                    userEntity?.let { it.toUser() to member.role }
                }
            }
        }
    }

    fun createProject(project: Project) {
        viewModelScope.launch {
            repository.createProject(project.toEntity())
        }
    }

    fun updateProject(project: Project) {
        viewModelScope.launch {
            repository.updateProject(project.toEntity())
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch {
            repository.deleteProject(project.toEntity())
        }
    }

    fun joinProject(code: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = currentUser.value
            if (user != null) {
                val success = repository.joinProject(code, user.id)
                onResult(success)
            } else {
                onResult(false)
            }
        }
    }

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    fun seedData() {
        viewModelScope.launch {
            _isSyncing.value = true
            // Simulate network delay
            kotlinx.coroutines.delay(1500)
            SampleData.seedDatabase(repository)
            _isSyncing.value = false
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
    private fun UserEntity.toUser(): User {
        return User(
            id = id,
            name = name,
            email = email,
            username = username,
            profilePictureUrl = profilePictureUrl,
            authProvider = authProvider
        )
    }

    private fun User.toEntity(createdAt: Long = System.currentTimeMillis(), lastLoginAt: Long = System.currentTimeMillis()): UserEntity {
        return UserEntity(
            id = id,
            name = name,
            email = email,
            username = username,
            profilePictureUrl = profilePictureUrl,
            authProvider = authProvider,
            createdAt = createdAt,
            lastLoginAt = lastLoginAt
        )
    }

    private fun ProjectEntity.toProject(owner: User): Project {
        return Project(
            id = id,
            title = title,
            description = description,
            category = category,
            code = code,
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
            code = code,
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
