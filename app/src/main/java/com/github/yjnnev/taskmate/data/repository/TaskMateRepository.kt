package com.github.yjnnev.taskmate.data.repository

import com.github.yjnnev.taskmate.data.local.dao.*
import com.github.yjnnev.taskmate.data.local.entity.*
import com.github.yjnnev.taskmate.classes.TaskStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class TaskMateRepository(
    private val userDao: UserDao,
    private val projectDao: ProjectDao,
    private val taskDao: TaskDao,
    private val projectMemberDao: ProjectMemberDao
) {
    // User operations
    suspend fun createOrUpdateUser(user: UserEntity) {
        val existing = userDao.getUserById(user.id)
        if (existing == null) {
            userDao.insertUser(user)
        } else {
            userDao.updateUser(user.copy(createdAt = existing.createdAt))
        }
    }

    suspend fun getUser(userId: String): UserEntity? = userDao.getUserById(userId)

    suspend fun getUserByEmail(email: String): UserEntity? = userDao.getUserByEmail(email)

    fun observeUser(userId: String): Flow<UserEntity?> = userDao.observeUser(userId)

    fun observeAllUsers(): Flow<List<UserEntity>> = userDao.observeAllUsers()

    // Project operations
    suspend fun createProject(project: ProjectEntity) {
        projectDao.insertProject(project)
        // Add owner as project member
        projectMemberDao.insertMember(
            ProjectMemberEntity(
                projectId = project.id,
                userId = project.ownerId,
                role = "OWNER",
                joinedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun updateProject(project: ProjectEntity) {
        projectDao.updateProject(project)
    }

    suspend fun deleteProject(project: ProjectEntity) {
        projectDao.deleteProject(project)
    }

    suspend fun joinProject(code: String, userId: String): Boolean {
        val project = projectDao.getProjectByCode(code) ?: return false
        
        // Check if already a member using getMember
        val existingMember = projectMemberDao.getMember(project.id, userId)
        
        if (existingMember != null) return true // Or handle as already joined

        projectMemberDao.insertMember(
            ProjectMemberEntity(
                projectId = project.id,
                userId = userId,
                role = "MEMBER",
                joinedAt = System.currentTimeMillis()
            )
        )
        return true
    }

    fun observeUserProjects(userId: String): Flow<List<ProjectEntity>> {
        return projectDao.observeAllUserProjects(userId)
    }

    suspend fun getProject(projectId: String): ProjectEntity? {
        return projectDao.getProjectById(projectId)
    }

    // Task operations
    suspend fun createTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }

    fun observeProjectTasks(projectId: String): Flow<List<TaskEntity>> {
        return taskDao.observeTasksByProject(projectId)
    }

    fun observeUserTasks(userId: String): Flow<List<TaskEntity>> {
        return taskDao.observeTasksAssignedToUser(userId)
    }

    suspend fun assignTask(taskId: String, userId: String?) {
        taskDao.assignTask(taskId, userId)
    }

    fun observeTaskCounts(projectId: String): Pair<Flow<Int>, Flow<Int>> {
        return Pair(
            taskDao.observeCompletedTaskCount(projectId),
            taskDao.observeTotalTaskCount(projectId)
        )
    }

    // Member operations
    suspend fun addProjectMember(projectId: String, userId: String, role: String = "MEMBER") {
        projectMemberDao.insertMember(
            ProjectMemberEntity(
                projectId = projectId,
                userId = userId,
                role = role,
                joinedAt = System.currentTimeMillis()
            )
        )
    }

    fun observeProjectMembers(projectId: String): Flow<List<ProjectMemberEntity>> {
        return projectMemberDao.observeProjectMembers(projectId)
    }

    fun observeMemberCount(projectId: String): Flow<Int> {
        return projectMemberDao.observeMemberCount(projectId)
    }
}