package com.github.yjnnev.taskmate.data.local.dao

import androidx.room.*
import com.github.yjnnev.taskmate.data.local.entity.TaskEntity
import com.github.yjnnev.taskmate.classes.TaskStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: String): TaskEntity?

    @Query("SELECT * FROM tasks WHERE projectId = :projectId ORDER BY CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END ASC, createdAt DESC")
    fun observeTasksByProject(projectId: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE assignedToUserId = :userId ORDER BY CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END ASC, dueDate ASC")
    fun observeTasksAssignedToUser(userId: String): Flow<List<TaskEntity>>

    @Query("SELECT COUNT(*) FROM tasks WHERE projectId = :projectId AND status = 'COMPLETED'")
    fun observeCompletedTaskCount(projectId: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE projectId = :projectId")
    fun observeTotalTaskCount(projectId: String): Flow<Int>

    @Query("UPDATE tasks SET status = :status, updatedAt = :updatedAt WHERE id = :taskId")
    suspend fun updateTaskStatus(taskId: String, status: TaskStatus, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE tasks SET assignedToUserId = :userId, updatedAt = :updatedAt WHERE id = :taskId")
    suspend fun assignTask(taskId: String, userId: String?, updatedAt: Long = System.currentTimeMillis())
}