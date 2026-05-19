package com.github.yjnnev.taskmate.classes

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val username: String = name, // Defaults to name if not specified
    val profilePictureUrl: String? = null, // For Google sign-in or custom profile pics
    val authProvider: AuthProvider = AuthProvider.EMAIL // Track how user signed in
)