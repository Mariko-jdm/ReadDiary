@file:OptIn(ExperimentalMaterial3Api::class)

package com.mariii.readdiary.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mariii.readdiary.navigation.Screen
import com.mariii.readdiary.ui.components.state.EmptyState
import com.mariii.readdiary.ui.theme.*


data class LibraryBook(
    val id: Int,
    val title: String,
    val author: String
)


// -------------------------
// экран с навигацией
// -------------------------
@Composable
fun LibraryScreen(navController: NavController) {

    val books = listOf(
        LibraryBook(1, "1984", "Оруэлл"),
        LibraryBook(2, "Книга 2", "Автор"),
        LibraryBook(3, "Книга 3", "Автор")
    )

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
    books: List<LibraryBook>,
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

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    items(filteredBooks) { book ->
                        Box(
                            modifier = Modifier
                                .aspectRatio(0.7f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceVariant)
                                .clickable { onBookClick(book.id) }
                        )
                    }
                }
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
    ReadDiaryTheme {
        LibraryScreenContent(
            books = listOf(
                LibraryBook(1, "Винo из одуванчиков", "Рэй Брэдбери"),
                LibraryBook(2, "Книга", "Автор")
            ),
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