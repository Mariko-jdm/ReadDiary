@file:OptIn(ExperimentalMaterial3Api::class)

package com.mariii.readdiary.ui.screen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mariii.readdiary.R
import com.mariii.readdiary.ui.components.progress.ProgressSection
import com.mariii.readdiary.ui.theme.Background
import com.mariii.readdiary.ui.theme.Dimens
import com.mariii.readdiary.ui.theme.OnBackground
import com.mariii.readdiary.ui.theme.ReadDiaryTheme
import com.mariii.readdiary.ui.theme.Surface

@Composable
fun StatisticsScreen() {

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
                    reminderText = "ежедневно в 12:00"
                )
            }
        }
    }
}