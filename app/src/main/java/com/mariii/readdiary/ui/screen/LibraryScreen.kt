@file:OptIn(ExperimentalMaterial3Api::class)

package com.mariii.readdiary.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mariii.readdiary.domain.model.Book
import com.mariii.readdiary.domain.model.ReadingStatus
import com.mariii.readdiary.navigation.Screen
import com.mariii.readdiary.ui.components.book.BookGrid
import com.mariii.readdiary.ui.components.state.EmptyState
import com.mariii.readdiary.ui.theme.AppTypography
import com.mariii.readdiary.ui.theme.Background
import com.mariii.readdiary.ui.theme.Dimens
import com.mariii.readdiary.ui.theme.OnButtonLight
import com.mariii.readdiary.ui.theme.OnSurface
import com.mariii.readdiary.ui.theme.PrimaryTransparent
import com.mariii.readdiary.ui.theme.ReadDiaryTheme
import com.mariii.readdiary.ui.theme.Surface
import com.mariii.readdiary.ui.viewmodel.BookViewModel


// -------------------------
// экран с навигацией
// -------------------------
@Composable
fun LibraryScreen(
    navController: NavController,
    viewModel: BookViewModel = viewModel()
) {

    val books = viewModel.books

    LibraryScreenContent(
        books = books,
        onAddClick = {
            navController.navigate(Screen.AddBook.route)
        },
        onBookClick = {
            navController.navigate(Screen.BookDetails.createRoute(it))
        }
    )
}


// -------------------------
// UI
// -------------------------
@Composable
fun LibraryScreenContent(
    books: List<Book>,
    onAddClick: () -> Unit,
    onBookClick: (Int) -> Unit
) {

    var searchQuery by remember { mutableStateOf("") }

    // фильтр
    val filteredBooks = books.filter {
        it.title.contains(searchQuery, true) ||
                it.author.contains(searchQuery, true)
    }

    Scaffold(
        containerColor = Background,

        topBar = {
            Column(
                modifier = Modifier
                    .background(Background)
                    .padding(top = 12.dp)
                    .padding(Dimens.paddingMedium)
            ) {

                // строка с меню + поиск
                Row(verticalAlignment = Alignment.CenterVertically) {

                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }

                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text("Поиск по названию/автору")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                                colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF3EFE7),
                        unfocusedContainerColor = Color(0xFFF3EFE7),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                    )
                }
            }
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

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(
                    start = Dimens.paddingMedium,
                    end = Dimens.paddingMedium,
                    bottom = 80.dp
                )
                .clip(RoundedCornerShape(Dimens.cornerRadius))
                .background(Surface)
                .padding(Dimens.paddingMedium)
        ) {

            // фильтры (пока заглушка)
            Row {
                FilterDropdown("Статус чтения")
                FilterDropdown("Категория")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // контент
            if (books.isEmpty()) {

                EmptyState(
                    text = "у вас пока нет книг",
                    modifier = Modifier.fillMaxWidth()
                )

            } else if (filteredBooks.isEmpty()) {

                EmptyState(
                    text = "ничего не найдено",
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

@Composable
fun FilterDropdown(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Text(
            text = text,
            style = AppTypography.bodyMedium,
            color = OnSurface
        )

        Spacer(modifier = Modifier.width(4.dp))

        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null
        )
    }
}


// -------------------------
// preview
// -------------------------
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LibraryPreview_WithData() {

    val books = listOf(
        Book(
            id = 1,
            title = "Вино из одуванчиков",
            author = "Рэй Брэдбери",
            totalPages = 300,
            currentPage = 120,
            status = ReadingStatus.READING
        ),
        Book(
            id = 2,
            title = "Маленький принц",
            author = "Экзюпери",
            totalPages = 150,
            currentPage = 150,
            status = ReadingStatus.FINISHED
        ))

    ReadDiaryTheme {
        LibraryScreenContent(
            books = books,
            onAddClick = {},
            onBookClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LibraryPreview_Empty() {
    ReadDiaryTheme {
        LibraryScreenContent(
            books = emptyList(),
            onAddClick = {},
            onBookClick = {}
        )
    }
}