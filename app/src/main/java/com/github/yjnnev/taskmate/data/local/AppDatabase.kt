package com.github.yjnnev.taskmate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.yjnnev.taskmate.data.local.dao.*
import com.github.yjnnev.taskmate.data.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        ProjectEntity::class,
        TaskEntity::class,
        ProjectMemberEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao
    abstract fun projectMemberDao(): ProjectMemberDao
}