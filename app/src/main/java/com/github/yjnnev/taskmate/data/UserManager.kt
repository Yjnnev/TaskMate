package com.github.yjnnev.taskmate.data

import android.content.Context
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
    private val sharedPrefs by lazy {
        AppModule.getContext().getSharedPreferences("taskmate_prefs", Context.MODE_PRIVATE)
    }

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId: StateFlow<String?> = _currentUserId.asStateFlow()

    init {
        _currentUserId.value = sharedPrefs.getString("logged_in_user_id", null)
        
        scope.launch {
            // Seed dummy user for testing
            val dummyEmail = "test@example.com"
            if (repository.getUserByEmail(dummyEmail) == null) {
                repository.createOrUpdateUser(UserEntity(
                    id = "dummy_id",
                    name = "Test User",
                    email = dummyEmail,
                    password = "test123",
                    username = "testuser",
                    profilePictureUrl = null,
                    authProvider = AuthProvider.EMAIL,
                    createdAt = System.currentTimeMillis(),
                    lastLoginAt = System.currentTimeMillis()
                ))
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
        sharedPrefs.edit().putString("logged_in_user_id", userId).apply()
    }

    suspend fun signInWithEmail(email: String, password: String): Boolean {
        val trimmedEmail = email.trim()
        val trimmedPassword = password.trim()

        // 1. Check if user exists in Database
        var userEntity = repository.getUserByEmail(trimmedEmail)

        // 2. If not in DB, check SampleData
        if (userEntity == null) {
            val sampleUser = SampleData.users.find { it.email.equals(trimmedEmail, ignoreCase = true) }
            if (sampleUser != null) {
                userEntity = UserEntity(
                    id = sampleUser.id,
                    name = sampleUser.name,
                    email = sampleUser.email,
                    password = sampleUser.password,
                    username = sampleUser.username,
                    profilePictureUrl = sampleUser.profilePictureUrl,
                    authProvider = sampleUser.authProvider,
                    createdAt = System.currentTimeMillis(),
                    lastLoginAt = System.currentTimeMillis()
                )
                repository.createOrUpdateUser(userEntity)
            }
        }

        // 3. If still null, user does not exist
        if (userEntity == null) return false

        // 4. Validate password
        if (userEntity.password != trimmedPassword) return false

        _currentUserId.value = userEntity.id
        sharedPrefs.edit().putString("logged_in_user_id", userEntity.id).apply()
        return true
    }

    suspend fun signUp(name: String, email: String, username: String, password: String): Boolean {
        // Check if user already exists
        if (repository.getUserByEmail(email) != null || SampleData.users.any { it.email == email }) {
            return false
        }

        val userEntity = UserEntity(
            id = "user_${System.currentTimeMillis()}",
            name = name,
            email = email,
            password = password,
            username = username,
            profilePictureUrl = null,
            authProvider = AuthProvider.EMAIL,
            createdAt = System.currentTimeMillis(),
            lastLoginAt = System.currentTimeMillis()
        )
        
        repository.createOrUpdateUser(userEntity)
        _currentUserId.value = userEntity.id
        sharedPrefs.edit().putString("logged_in_user_id", userEntity.id).apply()
        return true
    }

    suspend fun signInWithGoogle(googleUser: UserEntity) {
        repository.createOrUpdateUser(googleUser)
        _currentUserId.value = googleUser.id
        sharedPrefs.edit().putString("logged_in_user_id", googleUser.id).apply()
    }

    suspend fun updateUserProfile(name: String, username: String, profilePictureUrl: String?) {
        val userId = _currentUserId.value ?: return
        val currentEntity = repository.getUser(userId) ?: return
        val updatedEntity = currentEntity.copy(
            name = name,
            username = username,
            profilePictureUrl = profilePictureUrl
        )
        repository.createOrUpdateUser(updatedEntity)
    }

    fun signOut() {
        _currentUserId.value = null
        sharedPrefs.edit().remove("logged_in_user_id").apply()
    }

    // Extension function to convert UserEntity to User
    private fun UserEntity.toUser(): User {
        return User(
            id = id,
            name = name,
            email = email,
            password = password,
            username = username,
            profilePictureUrl = profilePictureUrl,
            authProvider = authProvider
        )
    }
}