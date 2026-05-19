package com.github.yjnnev.taskmate.data.local.dao

import androidx.room.*
import com.github.yjnnev.taskmate.data.local.entity.ProjectMemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectMemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: ProjectMemberEntity)

    @Delete
    suspend fun removeMember(member: ProjectMemberEntity)

    @Query("SELECT * FROM project_members WHERE projectId = :projectId")
    fun observeProjectMembers(projectId: String): Flow<List<ProjectMemberEntity>>

    @Query("SELECT * FROM project_members WHERE userId = :userId")
    fun observeUserProjects(userId: String): Flow<List<ProjectMemberEntity>>

    @Query("SELECT COUNT(*) FROM project_members WHERE projectId = :projectId")
    fun observeMemberCount(projectId: String): Flow<Int>

    @Query("DELETE FROM project_members WHERE projectId = :projectId AND userId = :userId")
    suspend fun removeMemberFromProject(projectId: String, userId: String)

    @Query("SELECT * FROM project_members WHERE projectId = :projectId AND userId = :userId")
    suspend fun getMember(projectId: String, userId: String): ProjectMemberEntity?
}