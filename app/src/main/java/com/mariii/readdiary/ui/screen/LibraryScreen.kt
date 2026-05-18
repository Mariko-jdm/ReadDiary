@file:OptIn(ExperimentalMaterial3Api::class)
package com.mariii.readdiary.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mariii.readdiary.R
import com.mariii.readdiary.domain.model.Book
import com.mariii.readdiary.domain.model.ReadingStatus
import com.mariii.readdiary.navigation.Screen
import com.mariii.readdiary.ui.components.book.BookGrid
import com.mariii.readdiary.ui.components.book.statusToText
import com.mariii.readdiary.ui.components.state.EmptyState
import com.mariii.readdiary.ui.theme.AppTypography
import com.mariii.readdiary.ui.theme.Background
import com.mariii.readdiary.ui.theme.Dimens
import com.mariii.readdiary.ui.theme.OnButtonLight
import com.mariii.readdiary.ui.theme.OnSurface
import com.mariii.readdiary.ui.theme.Primary
import com.mariii.readdiary.ui.theme.ReadDiaryTheme
import com.mariii.readdiary.ui.theme.Surface
import com.mariii.readdiary.ui.viewmodel.BookViewModel

@Composable
fun LibraryScreen(
    navController: NavController,
    viewModel: BookViewModel = viewModel()
) {
    val books by viewModel.books.collectAsState()

    LibraryScreenContent(
        books = books,
        onAddClick = { navController.navigate(Screen.AddBook.route) },
        onBookClick = { book ->
            navController.navigate(Screen.BookDetails.createRoute(book.id))
        }
    )
}

@Composable
fun LibraryScreenContent(
    books: List<Book>,
    onAddClick: () -> Unit,
    onBookClick: (Book) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf<ReadingStatus?>(null) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    var statusExpanded by remember { mutableStateOf(false) }
    var categoryExpanded by remember { mutableStateOf(false) }

    // Фильтрация
    val filteredBooks = books.filter { book ->
        val matchesSearch = searchQuery.isBlank() ||
                book.title.contains(searchQuery, ignoreCase = true) ||
                book.author.contains(searchQuery, ignoreCase = true)

        val matchesStatus = selectedStatus == null || book.status == selectedStatus
        val matchesCategory = selectedCategory == null || book.category == selectedCategory

        matchesSearch && matchesStatus && matchesCategory
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }

                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text(stringResource(R.string.search_name_author)) },
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
                containerColor = Primary
            ) {
                Icon(Icons.Default.Add, null, tint = OnButtonLight)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(start = Dimens.paddingMedium, end = Dimens.paddingMedium, bottom = 80.dp)
                .clip(RoundedCornerShape(Dimens.cornerRadius))
                .background(Surface)
                .padding(Dimens.paddingMedium)
        ) {

            Row {
                FilterDropdown(
                    text = selectedStatus?.let { statusToText(it) } ?: stringResource(R.string.reading_status),
                    expanded = statusExpanded,
                    onDismiss = { statusExpanded = false },
                    onClick = { statusExpanded = true },
                    onStatusSelected = { selectedStatus = it }
                )

                FilterDropdown(
                    text = selectedCategory ?: stringResource(R.string.category),
                    expanded = categoryExpanded,
                    onDismiss = { categoryExpanded = false },
                    onClick = { categoryExpanded = true },
                    onCategorySelected = { selectedCategory = it },
                    allBooks = books
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (books.isEmpty()) {
                EmptyState(text = stringResource(R.string.empty_library), modifier = Modifier.fillMaxWidth())
            } else if (filteredBooks.isEmpty()) {
                EmptyState(text = stringResource(R.string.nothing_found), modifier = Modifier.fillMaxWidth())
            } else {
                BookGrid(
                    books = filteredBooks,
                    emptyText = stringResource(R.string.empty_library),
                    onBookClick = onBookClick
                )
            }
        }
    }
}

// ==================== ФИЛЬТР ====================

@Composable
private fun FilterDropdown(
    text: String,
    expanded: Boolean,
    onDismiss: () -> Unit,
    onClick: () -> Unit,
    onStatusSelected: ((ReadingStatus?) -> Unit)? = null,
    onCategorySelected: ((String?) -> Unit)? = null,
    allBooks: List<Book> = emptyList()
) {
    Box {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(vertical = 8.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = AppTypography.bodyMedium,
                color = OnSurface
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = OnSurface
            )
        }

        // Меню
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
            containerColor = Surface
        ) {
            if (onStatusSelected != null) {
                // Статус
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.reading_status)) },
                    onClick = {
                        onStatusSelected(null)
                        onDismiss()
                    }
                )
                ReadingStatus.entries.forEach { status ->
                    DropdownMenuItem(
                        text = { Text(statusToText(status)) },
                        onClick = {
                            onStatusSelected(status)
                            onDismiss()
                        }
                    )
                }
            } else {
                // Категория
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.category)) },
                    onClick = {
                        onCategorySelected?.invoke(null)
                        onDismiss()
                    }
                )
                allBooks.map { it.category }.distinct().sorted().forEach { cat ->
                    DropdownMenuItem(
                        text = { Text(cat) },
                        onClick = {
                            onCategorySelected?.invoke(cat)
                            onDismiss()
                        }
                    )
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