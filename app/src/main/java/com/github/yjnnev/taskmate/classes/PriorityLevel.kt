package com.github.yjnnev.taskmate.classes

enum class PriorityLevel(val displayName: String, val color: Long) {
    LOW("Low", 0xFF6B7280),
    MEDIUM("Medium", 0xFFF59E0B),
    HIGH("High", 0xFFEF4444),
    URGENT("Urgent", 0xFF7C3AED)
}