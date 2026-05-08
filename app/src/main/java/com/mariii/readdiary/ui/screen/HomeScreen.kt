@file:OptIn(ExperimentalMaterial3Api::class)

package com.mariii.readdiary.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mariii.readdiary.R
import com.mariii.readdiary.domain.model.Book
import com.mariii.readdiary.domain.model.ReadingStatus
import com.mariii.readdiary.navigation.Screen
import com.mariii.readdiary.ui.components.book.BookCard
import com.mariii.readdiary.ui.components.book.BookGrid
import com.mariii.readdiary.ui.components.common.PrimaryButton
import com.mariii.readdiary.ui.components.state.EmptyState
import com.mariii.readdiary.ui.theme.AppTypography
import com.mariii.readdiary.ui.theme.Background
import com.mariii.readdiary.ui.theme.Dimens
import com.mariii.readdiary.ui.theme.OnBackground
import com.mariii.readdiary.ui.theme.OnButtonLight
import com.mariii.readdiary.ui.theme.OnSurface
import com.mariii.readdiary.ui.theme.PrimaryTransparent
import com.mariii.readdiary.ui.theme.ReadDiaryTheme
import com.mariii.readdiary.ui.theme.Surface
import com.mariii.readdiary.ui.viewmodel.BookViewModel

// -------------------------
// основной экран (с навигацией)
// -------------------------
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: BookViewModel = viewModel()
){

    val books = viewModel.books

    HomeScreenContent(
        books = books,
        onAddClick = {
            navController.navigate(Screen.AddBook.route)
        },
        onBookClick = { id ->
            navController.navigate(Screen.BookDetails.createRoute(id))
        },
        onContinueClick = { id ->
            navController.navigate(Screen.BookDetails.createRoute(id))
        }
    )
}


// -------------------------
// UI без навигации (для preview)
// -------------------------
@Composable
fun HomeScreenContent(
    books: List<Book>,
    onAddClick: () -> Unit,
    onBookClick: (Int) -> Unit,
    onContinueClick: (Int) -> Unit
) {

    Scaffold(
        containerColor = Background,

        topBar = {
            TopAppBar(
                title = { Text("Главная", color = OnBackground) },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Menu, contentDescription = null, tint = OnBackground)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = PrimaryTransparent
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = OnButtonLight)
            }
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(Dimens.paddingMedium),
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
        ) {

            // блок "продолжить чтение"
            if (books.isNotEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        val book = books.first()

                        BookCard(
                            title = book.title,
                            author = book.author
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Text(
                                    text = book.progressText,
                                    style = AppTypography.bodyMedium,
                                    color = OnSurface
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                PrimaryButton(
                                    text = "Продолжить",
                                    onClick = {},
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.triangle_play),
                                            contentDescription = null,
                                            tint = OnSurface,
                                            modifier = Modifier
                                                .size(20.dp)
                                                .rotate(180f) // поворот
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = "Добавить заметку",
                                    style = AppTypography.bodyMedium.copy(
                                        textDecoration = TextDecoration.Underline
                                    ),
                                    color = OnSurface
                                )
                            }
                        }
                    }
                }
            }

            // блок "читаю сейчас"
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(Dimens.cornerRadius))
                        .background(Surface)
                        .padding(Dimens.paddingMedium)
                ) {

                    Text(
                        text = "Читаю сейчас",
                        style = AppTypography.titleMedium,
                        color = OnBackground
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (books.isEmpty()) {

                        EmptyState(
                            text = "у вас пока нет книг",
                            modifier = Modifier.fillMaxWidth()
                        )

                    } else {
                        BookGrid(
                            books = books,
                            emptyText = "у вас пока нет книг",
                            onBookClick = onBookClick
                        )
                    }
                }
            }
        }
    }
}


// -------------------------
// preview
// -------------------------
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview_Empty() {
    ReadDiaryTheme {
        HomeScreenContent(
            books = emptyList(),
            onAddClick = {},
            onBookClick = {},
            onContinueClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview_WithData() {

    val books = listOf(
        Book(
            id = 1,
            title = "Вино из одуванчиков",
            author = "Рэй Брэдбери",
            totalPages = 300,
            currentPage = 120,
            status = ReadingStatus.READING
        )
    )

    ReadDiaryTheme {
        HomeScreenContent(
            books = books,
            onAddClick = {},
            onBookClick = {},
            onContinueClick = {}
        )
    }
}