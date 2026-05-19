package com.github.yjnnev.taskmate.ui.screens

// IMPORTANT: Replace this with YOUR actual package name
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.yjnnev.taskmate.R
import com.github.yjnnev.taskmate.ui.theme.NavyDark
import com.github.yjnnev.taskmate.ui.theme.TaskMatePurple

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark)
            .systemBarsPadding() // 1. Prevents Edge-to-Edge from clipping top or bottom content
    ) {
        // --- TOP HEADER SECTION (1/3 of screen) ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 32.dp, vertical = 24.dp)
        ) {
            Spacer(modifier = Modifier.weight(0.7f))

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

            Text("Welcome back", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text("Academic Collaboration", color = Color.White.copy(alpha = 0.6f), fontSize = 15.sp)

            Spacer(modifier = Modifier.weight(0.3f))
        }

        // --- WHITE FORM SECTION (2/3 of screen) ---
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize() // Fills the remaining 2/3 strictly
                    .padding(horizontal = 28.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Group 1: Input Fields (Keep small fixed spaces between tightly coupled items)
                CustomTextField(
                    label = "Email",
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "you@csu.edu.ph",
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomTextField(
                    label = "Password",
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "••••••••",
                    keyboardType = KeyboardType.Password,
                    isPassword = true
                )

                TextButton(
                    onClick = { /* Forgot password */ },
                    modifier = Modifier.align(Alignment.End),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Forgot password?", color = TaskMatePurple, fontSize = 13.sp)
                }

                // Elastic Gap 1: Shrinks on smaller devices
                Spacer(modifier = Modifier.weight(1f))

                // Group 2: Primary Action
                Button(
                    onClick = { /* Handle login */ },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TaskMatePurple)
                ) {
                    Text("Sign in", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                // Elastic Gap 2: Shrinks on smaller devices
                Spacer(modifier = Modifier.weight(1f))

                // Group 3: Alternates / Social
                Row(verticalAlignment = Alignment.CenterVertically) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
                    Text(" or continue with ", color = Color.Gray, fontSize = 12.sp)
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    SocialLoginButton(
                        icon = R.drawable.ic_google,
                        text = "Google",
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    )
                    SocialLoginButton(
                        icon = R.drawable.ic_github,
                        text = "GitHub",
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Elastic Gap 3: Pushes the footer down proportionally
                Spacer(modifier = Modifier.weight(1.5f))

                // Group 4: Footer
                Row {
                    Text("No account? ", color = Color.Gray, fontSize = 14.sp)
                    Text(
                        "Sign up",
                        color = TaskMatePurple,
                        modifier = Modifier.clickable { /* Navigate */ },
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

// Reusable Custom TextField to keep the code clean
@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    isPassword: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Color.LightGray) },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TaskMatePurple,
                unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
fun SocialLoginButton(
    icon: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray) // Makes the border visible
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // WE USE IMAGE HERE INSTEAD OF ICON
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = Color.Black, // Ensures the text is visible on the white background
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}