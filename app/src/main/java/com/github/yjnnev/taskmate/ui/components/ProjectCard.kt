package com.github.yjnnev.taskmate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.yjnnev.taskmate.R // Replace with your actual package name

@Composable
fun ProjectCard(
    title: String,
    subtitle: String,
    memberCount: Int,
    completedTasks: Int,
    totalTasks: Int
) {
    val isCompleted = completedTasks == totalTasks
    val progress = if (totalTasks > 0) completedTasks.toFloat() / totalTasks.toFloat() else 0f

    // Checkmark color logic: Green if all tasks are done, else gray
    val checkmarkColor = if (isCompleted) Color(0xFF4CAF50) else Color.LightGray

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F9FA) // Very light gray background
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // --- Row 1: Icon, Title, Subtitle ---
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0xFFEBF4FF), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_ghost),
                        contentDescription = "Project Icon",
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF1E88E5)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Row 2: Checkmark, Member Count, Task Count ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Checkmark and Status
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_checkmark),
                        contentDescription = "Status",
                        modifier = Modifier.size(18.dp),
                        tint = checkmarkColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isCompleted) "Completed" else "In Progress",
                        fontSize = 12.sp,
                        color = checkmarkColor,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Members and Tasks Count
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_ghost), // Using ghost as placeholder for members
                        contentDescription = "Members",
                        modifier = Modifier.size(14.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$memberCount",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "$completedTasks/$totalTasks Tasks",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- Row 3: Progress Bar ---
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = if (isCompleted) Color(0xFF4CAF50) else Color(0xFF1E88E5), // Green if complete, Blue otherwise
                trackColor = Color(0xFFE0E0E0),
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProjectCardPreview(){
    ProjectCard("Project1", "some project", 3, 2, 5)
}