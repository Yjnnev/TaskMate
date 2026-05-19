package com.github.yjnnev.taskmate.di

import android.content.Context
import androidx.room.Room
import com.github.yjnnev.taskmate.data.local.AppDatabase
import com.github.yjnnev.taskmate.data.repository.TaskMateRepository

object AppModule {
    private var database: AppDatabase? = null
    private var repository: TaskMateRepository? = null

    fun provide(context: Context) {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "taskmate_db"
            ).build()
        }
        if (repository == null) {
            val db = database!!
            repository = TaskMateRepository(
                userDao = db.userDao(),
                projectDao = db.projectDao(),
                taskDao = db.taskDao(),
                projectMemberDao = db.projectMemberDao()
            )
        }
    }

    fun getRepository(): TaskMateRepository {
        return repository ?: throw IllegalStateException("AppModule not initialized")
    }
}
