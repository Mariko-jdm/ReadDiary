@file:OptIn(ExperimentalMaterial3Api::class)

package com.mariii.readdiary.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mariii.readdiary.R
import com.mariii.readdiary.navigation.Screen
import com.mariii.readdiary.ui.components.book.BookCard
import com.mariii.readdiary.ui.components.common.PrimaryButton
import com.mariii.readdiary.ui.components.state.EmptyState
import com.mariii.readdiary.ui.theme.*

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val progress: String
)


// -------------------------
// основной экран (с навигацией)
// -------------------------
@Composable
fun HomeScreen(navController: NavController) {

    val books = listOf(
        Book(1, "Название книги", "Автор", "130 из 300 стр. (35%)"),
        Book(2, "Книга 2", "Автор", "50 из 200 стр."),
        Book(3, "Книга 3", "Автор", "10 из 100 стр.")
    )

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
                                    text = book.progress,
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

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 400.dp)

                        ) {

                            items(books) { book ->
                                Box(
                                    modifier = Modifier
                                        .aspectRatio(0.7f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(SurfaceVariant)
                                        .clickable {
                                            onBookClick(book.id)
                                        }
                                )
                            }
                        }
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
    ReadDiaryTheme {
        HomeScreenContent(
            books = listOf(
                Book(1, "Винo из одуванчиков", "Рэй Брэдбери", "120 из 300 стр. (40%)"),
                Book(2, "Винo из одуванчиков", "Рэй Брэдбери", "120 из 300 стр. (40%)"),
                Book(2, "Винo из одуванчиков", "Рэй Брэдбери", "120 из 300 стр. (40%)"),
                Book(2, "Винo из одуванчиков", "Рэй Брэдбери", "120 из 300 стр. (40%)"),Book(2, "Винo из одуванчиков", "Рэй Брэдбери", "120 из 300 стр. (40%)"),Book(2, "Винo из одуванчиков", "Рэй Брэдбери", "120 из 300 стр. (40%)"),
                Book(2, "Винo из одуванчиков", "Рэй Брэдбери", "120 из 300 стр. (40%)"),
                Book(2, "Винo из одуванчиков", "Рэй Брэдбери", "120 из 300 стр. (40%)"),


            ),
            onAddClick = {},
            onBookClick = {},
            onContinueClick = {}
        )
    }
}