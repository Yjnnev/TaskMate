package com.github.yjnnev.taskmate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.github.yjnnev.taskmate.R
import com.github.yjnnev.taskmate.data.UserManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onBack: () -> Unit) {
    val currentUser by UserManager.currentUser.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isDark = isSystemInDarkTheme()
    
    var name by remember(currentUser) { mutableStateOf(currentUser?.name ?: "") }
    var username by remember(currentUser) { mutableStateOf(currentUser?.username ?: "") }
    var selectedIcon by remember(currentUser) { mutableStateOf(currentUser?.profilePictureUrl) }
    
    val availableIcons = listOf(
        "ic_ghost",
        "ic_android",
        "ic_taskmate",
        "ic_github",
        "ic_google"
    )

    fun getResourceId(name: String): Int {
        return context.resources.getIdentifier(name, "drawable", context.packageName)
    }

    // Define which icons should be tinted and which should use original colors
    fun getIconTint(iconName: String): Color {
        return when (iconName) {
            "ic_ghost", "ic_github" -> if (isDark) Color.White else Color.Black
            "ic_android" -> Color.Unspecified // Already has its own green
            else -> Color.Unspecified // Brand icons
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        scope.launch {
                            UserManager.updateUserProfile(name, username, selectedIcon)
                            onBack()
                        }
                    }) {
                        Text("Save", fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Picture Preview
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                if (selectedIcon != null) {
                    val resId = getResourceId(selectedIcon!!)
                    if (resId != 0) {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Icon(
                                painter = painterResource(id = resId),
                                contentDescription = "Profile Picture",
                                tint = getIconTint(selectedIcon!!),
                                modifier = Modifier
                                    .padding(24.dp)
                                    .fillMaxSize()
                            )
                        }
                    } else {
                        AsyncImage(
                            model = selectedIcon,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF1E88E5), Color(0xFF1565C0))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = UserManager.getInitials(name),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Select an Icon",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.Start)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                // Option for no icon (initials)
                item {
                    ProfileIconOption(
                        isSelected = selectedIcon == null,
                        onClick = { selectedIcon = null }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    if (selectedIcon == null) 
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                    else Color.Transparent
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = UserManager.getInitials(name),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedIcon == null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                
                items(availableIcons) { iconName ->
                    val resId = getResourceId(iconName)
                    if (resId != 0) {
                        ProfileIconOption(
                            isSelected = selectedIcon == iconName,
                            onClick = { selectedIcon = iconName }
                        ) {
                            Icon(
                                painter = painterResource(id = resId),
                                contentDescription = null,
                                tint = getIconTint(iconName),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Text Fields
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                prefix = { Text("@") }
            )

            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Your email address is " + (currentUser?.email ?: ""),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

@Composable
fun ProfileIconOption(
    isSelected: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .border(
                width = 2.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = CircleShape
            ),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(2.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape),
                        tint = Color.White
                    )
                }
            }
        }
    }
}
