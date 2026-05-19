package com.github.yjnnev.taskmate.data.local

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.github.yjnnev.taskmate.classes.TaskStatus
import com.github.yjnnev.taskmate.data.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(dateFormatter)
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, dateFormatter) }
    }

    @TypeConverter
    fun fromTaskStatus(status: TaskStatus): String {
        return status.name
    }

    @TypeConverter
    fun toTaskStatus(status: String): TaskStatus {
        return TaskStatus.valueOf(status)
    }

    @TypeConverter
    fun fromPriorityLevel(priority: PriorityLevel): String {
        return priority.name
    }

    @TypeConverter
    fun toPriorityLevel(priority: String): PriorityLevel {
        return PriorityLevel.valueOf(priority)
    }

    @TypeConverter
    fun fromProjectCategory(category: ProjectCategory): String {
        return category.name
    }

    @TypeConverter
    fun toProjectCategory(category: String): ProjectCategory {
        return ProjectCategory.valueOf(category)
    }

    @TypeConverter
    fun fromAuthProvider(provider: AuthProvider): String {
        return provider.name
    }

    @TypeConverter
    fun toAuthProvider(provider: String): AuthProvider {
        return AuthProvider.valueOf(provider)
    }

    @TypeConverter
    fun fromMemberRole(role: MemberRole): String {
        return role.name
    }

    @TypeConverter
    fun toMemberRole(role: String): MemberRole {
        return MemberRole.valueOf(role)
    }
}