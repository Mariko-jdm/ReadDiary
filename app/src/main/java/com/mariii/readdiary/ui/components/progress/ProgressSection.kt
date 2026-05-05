package com.mariii.readdiary.ui.components.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.mariii.readdiary.ui.theme.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProgressSection(
    booksRead: Int,
    pagesRead: Int,
    goal: Int,
    goalProgress: Float,
    reminderText: String
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedPeriod by remember { mutableStateOf("за год") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.cornerRadius))
            .background(Surface)
            .padding(Dimens.paddingMedium)
    ) {

        // Выпадающий список
        Box {
            TextButton(onClick = { expanded = true }) {
                Text(
                    text = "Статистика $selectedPeriod",
                    color = OnSurface
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("за месяц") },
                    onClick = {
                        selectedPeriod = "за месяц"
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("за год") },
                    onClick = {
                        selectedPeriod = "за год"
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.paddingMedium))

        // График
        SimpleBarChart()

        Spacer(modifier = Modifier.height(Dimens.paddingMedium))

        // 📚 Статистика
        Text("Всего книг прочитано: $booksRead", color = OnSurface)
        Text("Всего страниц прочитано: $pagesRead", color = OnSurface)

        Spacer(modifier = Modifier.height(Dimens.paddingMedium))

        // Цель
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Цель: $goal книг",
                modifier = Modifier.weight(1f),
                color = OnSurface
            )

            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Edit, contentDescription = null)
            }
        }

        Text(
            text = "Цель выполнена на ${(goalProgress * 100).toInt()}%",
            color = OnSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { goalProgress },
            color = Primary,
            trackColor = Outline,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.height(Dimens.paddingMedium))

        // ⏰ Напоминание
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Напоминание: $reminderText",
                modifier = Modifier.weight(1f),
                color = OnSurface
            )

            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Edit, contentDescription = null)
            }
        }
    }
}

@Composable
fun SimpleBarChart() {
    val data = listOf(10, 40, 25, 60, 30, 50, 70)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEach { value ->
            Box(
                modifier = Modifier
                    .width(12.dp)
                    .fillMaxHeight(value / 100f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Primary)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressSectionPreview() {
    ReadDiaryTheme {
        ProgressSection(
            booksRead = 12,
            pagesRead = 2450,
            goal = 20,
            goalProgress = 0.6f,
            reminderText = "ежедневно в 12:00"
        )
    }
}