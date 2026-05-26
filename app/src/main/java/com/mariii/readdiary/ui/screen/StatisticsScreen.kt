@file:OptIn(ExperimentalMaterial3Api::class)

package com.mariii.readdiary.ui.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.mariii.readdiary.R
import com.mariii.readdiary.domain.model.ReadingStatus
import com.mariii.readdiary.ui.components.progress.ProgressSection
import com.mariii.readdiary.ui.theme.Background
import com.mariii.readdiary.ui.theme.Dimens
import com.mariii.readdiary.ui.theme.OnBackground
import com.mariii.readdiary.ui.theme.ReadDiaryTheme
import com.mariii.readdiary.ui.theme.Surface
import com.mariii.readdiary.ui.viewmodel.BookViewModel

@Composable
fun StatisticsScreen(
    viewModel: BookViewModel
) {

    val books by viewModel.books.collectAsState()

    // считаем все завершённые книги
    val finishedBooksList = books.filter {
        it.status == ReadingStatus.FINISHED
    }

    val finishedBooks = finishedBooksList.size

    // считаем все прочитанные страницы
    val pagesRead = books.sumOf { book ->

        when (book.status) {

            ReadingStatus.FINISHED -> {
                book.totalPages
            }

            ReadingStatus.READING -> {
                book.currentPage
            }

            else -> {
                0
            }
        }
    }

    val context = LocalContext.current

    val prefs = remember {
        context.getSharedPreferences("statistics_prefs", Context.MODE_PRIVATE)
    }

    var yearlyGoal by rememberSaveable {
        mutableIntStateOf(prefs.getInt("yearly_goal", 20))
    }

    var isEditingGoal by remember { mutableStateOf(false) }
    var goalInput by remember { mutableStateOf(yearlyGoal.toString()) }

    val goalProgress = if (yearlyGoal > 0) {
        finishedBooks.toFloat() / yearlyGoal.toFloat()
    } else 0f

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.statistics), color = OnBackground) },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Menu, contentDescription = "menu")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(Dimens.paddingMedium),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
        ) {

            ProgressSection(
                booksRead = finishedBooks,
                pagesRead = pagesRead,
                goal = yearlyGoal,
                goalProgress = goalProgress.coerceIn(0f, 1f),
                reminderText = "ежедневно в 12:00",
                isEditingGoal = isEditingGoal,
                goalInput = goalInput,
                onGoalInputChange = { goalInput = it },
                onGoalEditClick = {
                    if (isEditingGoal) {
                        yearlyGoal = goalInput.toIntOrNull() ?: yearlyGoal
                        prefs.edit {
                            putInt("yearly_goal", yearlyGoal)
                        }
                    } else {
                        goalInput = yearlyGoal.toString()
                    }
                    isEditingGoal = !isEditingGoal
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StatisticsScreenPreview() {
    ReadDiaryTheme {
        Scaffold(
            containerColor = Background,
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.statistics), color = OnBackground) },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Menu, contentDescription = "menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Background
                    )
                )
            },
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(Surface)
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(
                        start = Dimens.paddingMedium,
                        end = Dimens.paddingMedium,
                        bottom = 80.dp
                    )
            ) {

                ProgressSection(
                    booksRead = 12,
                    pagesRead = 2450,
                    goal = 20,
                    goalProgress = 0.55f,
                    reminderText = "ежедневно в 12:00",

                    // новые параметры
                    isEditingGoal = false,
                    goalInput = "20",
                    onGoalInputChange = {},
                    onGoalEditClick = {}
                )
            }
        }
    }
}