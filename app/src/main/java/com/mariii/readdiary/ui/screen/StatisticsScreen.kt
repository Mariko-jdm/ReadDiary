@file:OptIn(ExperimentalMaterial3Api::class)

package com.mariii.readdiary.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mariii.readdiary.ui.components.progress.ProgressSection
import com.mariii.readdiary.ui.theme.*

@Composable
fun StatisticsScreen() {

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = { Text("Статистика", color = OnBackground) },
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
                .padding(
                    Dimens.paddingMedium
                )
                .padding(bottom = 80.dp)
        ) {

            ProgressSection(
                booksRead = 12,
                pagesRead = 2450,
                goal = 20,
                goalProgress = 0.55f,
                reminderText = "ежедневно в 12:00"
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
                    title = { Text("Статистика", color = OnBackground) },
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
                // заглушка вместо настоящего BottomBar
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
                    reminderText = "ежедневно в 12:00"
                )
            }
        }
    }
}