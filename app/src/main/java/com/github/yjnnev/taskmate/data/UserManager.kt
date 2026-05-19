package com.github.yjnnev.taskmate.data

import com.github.yjnnev.taskmate.classes.AuthProvider
import com.github.yjnnev.taskmate.classes.User
import com.github.yjnnev.taskmate.data.local.entity.UserEntity
import com.github.yjnnev.taskmate.di.AppModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.getValue

object UserManager {
    private val repository by lazy { AppModule.getRepository() }
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId: StateFlow<String?> = _currentUserId.asStateFlow()

    init {
        // Automatically sign in a default user for now if none exists
        scope.launch {
            if (_currentUserId.value == null) {
                signInWithEmail("guest@taskmate.com", "password")
            }
        }
    }

    val currentUser: StateFlow<User?> = _currentUserId
        .flatMapLatest { userId ->
            if (userId != null) {
                repository.observeUser(userId).map { entity ->
                    entity?.toUser()
                }
            } else {
                flowOf(null)
            }
        }
        .stateIn(scope, SharingStarted.WhileSubscribed(5000), null)

    fun getInitials(name: String): String {
        if (name.isBlank()) return "?"
        return name.split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .map { it.first().uppercase() }
            .joinToString("")
    }

    // Set initial user (for testing or when user is already logged in)
    fun setCurrentUser(userId: String) {
        _currentUserId.value = userId
    }

    suspend fun signInWithEmail(email: String, password: String) {
        // TODO: Implement actual authentication
        val user = repository.getUserByEmail(email) ?: UserEntity(
            id = "user_${System.currentTimeMillis()}",
            name = email.substringBefore("@"),
            email = email,
            username = email.substringBefore("@"),
            profilePictureUrl = null,
            authProvider = AuthProvider.EMAIL,
            createdAt = System.currentTimeMillis(),
            lastLoginAt = System.currentTimeMillis()
        )
        repository.createOrUpdateUser(user)
        _currentUserId.value = user.id
    }

    suspend fun signInWithGoogle(googleUser: UserEntity) {
        repository.createOrUpdateUser(googleUser)
        _currentUserId.value = googleUser.id
    }

    fun signOut() {
        _currentUserId.value = null
    }

    // Extension function to convert UserEntity to User
    private fun UserEntity.toUser(): User {
        return User(
            id = id,
            name = name,
            email = email,
            username = username,
            profilePictureUrl = profilePictureUrl,
            authProvider = authProvider
        )
    }
}