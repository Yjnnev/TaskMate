package com.github.yjnnev.taskmate.data

import android.content.Context
import com.github.yjnnev.taskmate.di.AppModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object ThemeManager {
    private val sharedPrefs by lazy {
        AppModule.getContext().getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    }

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    init {
        _isDarkTheme.value = sharedPrefs.getBoolean("is_dark_theme", false)
    }

    fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        sharedPrefs.edit().putBoolean("is_dark_theme", isDark).apply()
    }
}
