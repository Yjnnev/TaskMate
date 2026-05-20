package com.github.yjnnev.taskmate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.yjnnev.taskmate.R
import com.github.yjnnev.taskmate.data.UserManager
import com.github.yjnnev.taskmate.ui.components.CustomTextField
import com.github.yjnnev.taskmate.ui.theme.NavyDark
import com.github.yjnnev.taskmate.ui.theme.TaskMatePurple
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(onBackToLogin: () -> Unit, onSignUpSuccess: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark)
            .systemBarsPadding()
    ) {
        // --- TOP HEADER SECTION ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
                .padding(horizontal = 32.dp, vertical = 24.dp)
        ) {
            Spacer(modifier = Modifier.weight(0.5f))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(42.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = TaskMatePurple
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_taskmate),
                        contentDescription = null,
                        modifier = Modifier.padding(0.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "TaskMate",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Create Account", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text("Join our academic community", color = Color.White.copy(alpha = 0.6f), fontSize = 15.sp)

            Spacer(modifier = Modifier.weight(0.3f))
        }

        // --- WHITE FORM SECTION ---
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2.2f),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 28.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTextField(
                    label = "Full Name",
                    value = name,
                    onValueChange = { name = it; errorMessage = null },
                    placeholder = "John Doe",
                    keyboardType = KeyboardType.Text
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomTextField(
                    label = "Email",
                    value = email,
                    onValueChange = { email = it; errorMessage = null },
                    placeholder = "you@csu.edu.ph",
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomTextField(
                    label = "Username",
                    value = username,
                    onValueChange = { username = it; errorMessage = null },
                    placeholder = "johndoe123",
                    keyboardType = KeyboardType.Text
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomTextField(
                    label = "Password",
                    value = password,
                    onValueChange = { password = it; errorMessage = null },
                    placeholder = "••••••••",
                    keyboardType = KeyboardType.Password,
                    isPassword = true
                )

                Spacer(modifier = Modifier.weight(1f))

                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        if (name.isBlank() || email.isBlank() || username.isBlank() || password.isBlank()) {
                            errorMessage = "Please fill in all fields"
                            return@Button
                        }
                        scope.launch {
                            val success = UserManager.signUp(name, email, username, password)
                            if (success) {
                                onSignUpSuccess()
                            } else {
                                errorMessage = "User already exists or registration failed"
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TaskMatePurple)
                ) {
                    Text("Sign up", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.weight(1f))

                Row {
                    Text("Already have an account? ", color = Color.Gray, fontSize = 14.sp)
                    Text(
                        "Sign in",
                        color = TaskMatePurple,
                        modifier = Modifier.clickable { onBackToLogin() },
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
