package com.github.yjnnev.taskmate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.yjnnev.taskmate.R

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

    // Pad Spacing around the card
    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White // Card Background
            ),
            shape = RoundedCornerShape(16.dp), // Border radius
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp, // Add elevation for card shadow effect
                pressedElevation = 8.dp // Elevation when pressed
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Row 1: Icon, Title, Subtitle
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(
                                Color(0xFFEBF4FF),
                                RoundedCornerShape(12.dp) // More rounded corners
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_ghost),
                            contentDescription = "Project Icon",
                            modifier = Modifier.size(28.dp),
                            tint = Color(0xFF1E88E5)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF1A1A1A)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = subtitle,
                            fontSize = 14.sp,
                            color = Color(0xFF6B7280)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp)) 

                // Row 2: Checkmark, Member Count, Task Count
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Checkmark and Status
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    checkmarkColor.copy(alpha = 0.1f),
                                    RoundedCornerShape(6.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_checkmark),
                                contentDescription = "Status",
                                modifier = Modifier.size(16.dp),
                                tint = checkmarkColor
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isCompleted) "Completed" else "In Progress",
                            fontSize = 13.sp,
                            color = checkmarkColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Members and Tasks Count
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Members section
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_ghost),
                                contentDescription = "Members",
                                modifier = Modifier.size(16.dp),
                                tint = Color(0xFF9CA3AF)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$memberCount",
                                fontSize = 13.sp,
                                color = Color(0xFF6B7280),
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Divider between members and tasks
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .width(1.dp)
                                .height(16.dp)
                                .background(Color(0xFFE5E7EB))
                        )

                        // Tasks section
                        Text(
                            text = "$completedTasks/$totalTasks Tasks",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1A1A1A)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Row 3: Progress Bar
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = if (isCompleted) Color(0xFF4CAF50) else Color(0xFF1E88E5),
                    trackColor = Color(0xFFF3F4F6),
                    strokeCap = StrokeCap.Round,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProjectCardPreview(){
    Column(modifier = Modifier.padding(16.dp)) {
        ProjectCard("Project Alpha", "Mobile App Development", 3, 2, 5)
        Spacer(modifier = Modifier.height(12.dp))
        ProjectCard("Project Beta", "Web Design Sprint", 5, 8, 8) // Completed project example
    }
}