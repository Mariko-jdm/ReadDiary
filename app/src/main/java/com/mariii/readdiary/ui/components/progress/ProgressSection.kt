package com.mariii.readdiary.ui.components.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mariii.readdiary.ui.components.book.MinimalTextField
import com.mariii.readdiary.ui.theme.AppTypography
import com.mariii.readdiary.ui.theme.Dimens
import com.mariii.readdiary.ui.theme.OnSurface
import com.mariii.readdiary.ui.theme.Outline
import com.mariii.readdiary.ui.theme.Primary
import com.mariii.readdiary.ui.theme.ReadDiaryTheme
import com.mariii.readdiary.ui.theme.Surface

@Composable
fun ProgressSection(
    booksRead: Int,
    pagesRead: Int,
    goal: Int,
    goalProgress: Float,
    reminderText: String,
    isEditingGoal: Boolean,
    goalInput: String,
    onGoalInputChange: (String) -> Unit,
    onGoalEditClick: () -> Unit
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

        // ---------- Период ----------
        Box(modifier = Modifier.fillMaxWidth()) {

            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable { expanded = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Статистика $selectedPeriod",
                    style = AppTypography.titleMedium,
                    color = OnSurface
                )

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = OnSurface
                )
            }

            DropdownMenu(
                expanded = expanded,
                containerColor = Surface,
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

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- График ----------
        SimpleBarChart()

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- Метрики ----------
        Text(
            text = "Всего книг прочитано: $booksRead",
            style = AppTypography.bodyMedium,
            color = OnSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Всего страниц прочитано: $pagesRead",
            style = AppTypography.bodyMedium,
            color = OnSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- Цель ----------
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Цель:",
                style = AppTypography.bodyMedium,
                color = OnSurface
            )

            Spacer(modifier = Modifier.width(8.dp))

            if (isEditingGoal) {

                Box(
                    modifier = Modifier.width(60.dp)
                ) {

                    MinimalTextField(

                        value = goalInput,

                        onValueChange = {

                            onGoalInputChange(
                                it.filter { char ->
                                    char.isDigit()
                                }
                            )
                        },

                        placeholder = "введите новую цель",

                        keyboardType = KeyboardType.Number
                    )
                }

            } else {

                Text(

                    text = "$goal книг",

                    modifier = Modifier.weight(1f),

                    style = AppTypography.bodyMedium,

                    color = OnSurface
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (isEditingGoal) {

                IconButton(

                    onClick = onGoalEditClick
                ) {

                    Icon(
                        Icons.Default.Check,
                        contentDescription = null
                    )
                }

            } else {

                IconButton(

                    onClick = {
                        onGoalInputChange(goal.toString())
                        onGoalEditClick()
                    }
                ) {

                    Icon(
                        Icons.Default.Edit,
                        contentDescription = null
                    )
                }
            }
        }

        Text(
            text = "Цель выполнена на ${(goalProgress * 100).toInt()}%",
            style = AppTypography.bodyMedium,
            color = OnSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { goalProgress },
            color = Primary,
            trackColor = Outline.copy(alpha = 0.9f),
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- Напоминание ----------
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Напоминание: $reminderText",
                modifier = Modifier.weight(1f),
                style = AppTypography.bodyMedium,
                color = OnSurface
            )

            IconButton(onClick = {}) {
                Icon(Icons.Default.Edit, contentDescription = null)
            }
        }
    }
}

@Composable
fun SimpleBarChart() {

    val dataPrimary = listOf(10, 30, 20, 50, 40, 60, 70)
    val dataSecondary = listOf(20, 20, 40, 30, 60, 70, 50)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {

        dataPrimary.indices.forEach { i ->

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .fillMaxHeight(dataPrimary[i] / 100f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.DarkGray)
                )

                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .fillMaxHeight(dataSecondary[i] / 100f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Primary)
                )
            }
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
            reminderText = "ежедневно в 12:00",

            // новые параметры для редактирования цели
            isEditingGoal = false,
            goalInput = "20",
            onGoalInputChange = {},
            onGoalEditClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProgressSectionEditingPreview() {
    ReadDiaryTheme {
        ProgressSection(
            booksRead = 12,
            pagesRead = 2450,
            goal = 20,
            goalProgress = 0.6f,
            reminderText = "ежедневно в 12:00",

            isEditingGoal = true,
            goalInput = "25",
            onGoalInputChange = {},
            onGoalEditClick = {}
        )
    }
}