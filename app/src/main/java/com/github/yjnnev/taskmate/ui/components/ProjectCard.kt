package com.github.yjnnev.taskmate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.yjnnev.taskmate.R
import com.github.yjnnev.taskmate.classes.Project
import com.github.yjnnev.taskmate.classes.ProjectCategory

@Composable
fun ProjectCard(
    project: Project,
    onClick: () -> Unit = {}
) {
    val isCompleted = project.completedTasks == project.totalTasks && project.totalTasks > 0
    val progress = if (project.totalTasks > 0) project.completedTasks.toFloat() / project.totalTasks.toFloat() else 0f
    val checkmarkColor = if (isCompleted) Color(0xFF4CAF50) else Color.LightGray

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() }  // Added clickable modifier
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Icon, Title, and Category Badge Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Icon Container - Fixed size
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(
                                project.category.color.copy(alpha = 0.1f),
                                RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = project.category.icon,
                            contentDescription = "Project Icon",
                            modifier = Modifier.size(28.dp),
                            tint = project.category.color
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Title and Category - Responsive layout
                    BoxWithConstraints(
                        modifier = Modifier.weight(1f)
                    ) {
                        val maxWidth = maxWidth

                        Column {
                            Text(
                                text = project.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = if (maxWidth < 240.dp) 16.sp else 18.sp,
                                color = Color(0xFF1A1A1A)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                CategoryBadge(category = project.category)

                                // Owner chip
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .background(
                                            Color(0xFFF3F4F6),
                                            RoundedCornerShape(6.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_ghost),
                                        contentDescription = "Owner",
                                        modifier = Modifier.size(12.dp),
                                        tint = Color(0xFF6B7280)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = project.owner.username,
                                        fontSize = 10.sp,
                                        color = Color(0xFF6B7280),
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }

                // Description if available
                if (project.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = project.description,
                        fontSize = 13.sp,
                        color = Color(0xFF6B7280),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Status and stats row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Status indicator
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
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1
                        )
                    }

                    // Members and Tasks
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_ghost),
                                contentDescription = "Members",
                                modifier = Modifier.size(16.dp),
                                tint = Color(0xFF9CA3AF)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${project.memberCount}",
                                fontSize = 13.sp,
                                color = Color(0xFF6B7280),
                                fontWeight = FontWeight.Medium,
                                maxLines = 1
                            )
                        }

                        Box(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .width(1.dp)
                                .height(16.dp)
                                .background(Color(0xFFE5E7EB))
                        )

                        Text(
                            text = "${project.completedTasks}/${project.totalTasks} Tasks",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1A1A1A),
                            maxLines = 1
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Progress bar
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = if (isCompleted) Color(0xFF4CAF50) else project.category.color,
                    trackColor = Color(0xFFF3F4F6),
                    strokeCap = StrokeCap.Round,
                )
            }
        }
    }
}

@Composable
fun CategoryBadge(category: ProjectCategory) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = category.color.copy(alpha = 0.1f)
    ) {
        Text(
            text = category.displayName,
            fontSize = 10.sp,
            color = category.color,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}