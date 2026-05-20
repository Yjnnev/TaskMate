package com.github.yjnnev.taskmate.data.local.dao

import androidx.room.*
import com.github.yjnnev.taskmate.data.local.entity.ProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: ProjectEntity)

    @Update
    suspend fun updateProject(project: ProjectEntity)

    @Delete
    suspend fun deleteProject(project: ProjectEntity)

    @Query("SELECT COUNT(*) FROM projects")
    suspend fun getProjectCount(): Int

    @Query("SELECT * FROM projects WHERE id = :projectId")
    suspend fun getProjectById(projectId: String): ProjectEntity?

    @Query("SELECT * FROM projects WHERE code = :code")
    suspend fun getProjectByCode(code: String): ProjectEntity?

    @Query("SELECT * FROM projects WHERE ownerId = :userId")
    fun observeProjectsByOwner(userId: String): Flow<List<ProjectEntity>>

    @Query("""
        SELECT * FROM projects 
        WHERE id IN (
            SELECT projectId FROM project_members 
            WHERE userId = :userId
        )
    """)
    fun observeProjectsByMember(userId: String): Flow<List<ProjectEntity>>

    @Query("""
        SELECT * FROM projects 
        WHERE ownerId = :userId 
        OR id IN (
            SELECT projectId FROM project_members 
            WHERE userId = :userId
        )
    """)
    fun observeAllUserProjects(userId: String): Flow<List<ProjectEntity>>
}