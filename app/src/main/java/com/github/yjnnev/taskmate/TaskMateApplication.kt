package com.github.yjnnev.taskmate

import android.app.Application
import com.github.yjnnev.taskmate.di.AppModule

class TaskMateApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppModule.provide(this)
    }
}
