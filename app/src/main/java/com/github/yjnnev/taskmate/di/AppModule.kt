package com.github.yjnnev.taskmate.di

import android.content.Context
import androidx.room.Room
import com.github.yjnnev.taskmate.data.local.AppDatabase
import com.github.yjnnev.taskmate.data.repository.TaskMateRepository

object AppModule {
    private var database: AppDatabase? = null
    private var repository: TaskMateRepository? = null
    private lateinit var applicationContext: Context

    fun provide(context: Context) {
        this.applicationContext = context.applicationContext
        if (database == null) {
            database = Room.databaseBuilder(
                this.applicationContext,
                AppDatabase::class.java,
                "taskmate_db"
            )
                .fallbackToDestructiveMigration()
                .build()
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

    fun getContext(): Context {
        if (!::applicationContext.isInitialized) throw IllegalStateException("AppModule not initialized")
        return applicationContext
    }
}
