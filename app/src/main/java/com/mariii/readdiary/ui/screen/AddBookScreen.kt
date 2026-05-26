@file:OptIn(ExperimentalMaterial3Api::class)

package com.mariii.readdiary.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mariii.readdiary.R
import com.mariii.readdiary.data.source.BookSearchSource
import com.mariii.readdiary.domain.model.Book
import com.mariii.readdiary.domain.model.ReadingStatus
import com.mariii.readdiary.ui.components.book.BookGrid
import com.mariii.readdiary.ui.components.book.EditableBookContent
import com.mariii.readdiary.ui.theme.Background
import com.mariii.readdiary.ui.theme.Dimens
import com.mariii.readdiary.ui.theme.Surface
import com.mariii.readdiary.ui.viewmodel.BookViewModel

@Composable
fun AddBookScreen(
    navController: NavController,
    viewModel: BookViewModel
) {

    AddBookScreenContent(

        onBackClick = {
            navController.popBackStack()
        },

        onSaveClick = { book ->

            viewModel.addBook(book)

            navController.popBackStack()
        }
    )
}

@Composable
fun AddBookScreenContent(
    onBackClick: () -> Unit,
    onSaveClick: (Book) -> Unit
) {

    var title by remember {
        mutableStateOf("")
    }

    var author by remember {
        mutableStateOf("")
    }

    var category by remember {
        mutableStateOf("")
    }

    var totalPages by remember {
        mutableStateOf("")
    }

    var currentPage by remember {
        mutableStateOf("")
    }

    var rating by remember {
        mutableIntStateOf(0)
    }

    var status by remember {
        mutableStateOf(ReadingStatus.PLANNED)
    }

    var coverUri by remember {
        mutableStateOf("")
    }


    var searchQuery by remember {
        mutableStateOf("")
    }

    val searchBooks = BookSearchSource
        .getBooks()
        .filter {

            it.title.contains(
                searchQuery,
                ignoreCase = true
            ) ||

                    it.author.contains(
                        searchQuery,
                        ignoreCase = true
                    )
        }

    Scaffold(

        containerColor = Background,

        topBar = {

            TopAppBar(

                title = {
                    Text(
                        stringResource(R.string.new_book)
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = onBackClick
                    ) {

                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            null
                        )
                    }
                },

                actions = {

                    IconButton(

                        onClick = {

                            onSaveClick(

                                Book(
                                    title = title,
                                    author = author,
                                    category = category,

                                    rating = rating,

                                    totalPages =
                                        totalPages.toIntOrNull() ?: 0,

                                    currentPage =
                                        currentPage.toIntOrNull() ?: 0,

                                    status = status,

                                    coverUri = coverUri,
                                )
                            )
                        }
                    ) {

                        Icon(
                            Icons.Default.Check,
                            null
                        )
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
                .padding(Dimens.paddingMedium)
        ) {

            EditableBookContent(

                title = title,
                onTitleChange = {
                    title = it
                },

                author = author,
                onAuthorChange = {
                    author = it
                },

                category = category,
                onCategoryChange = {
                    category = it
                },

                status = status,
                onStatusChange = {
                    status = it
                },

                rating = rating,
                onRatingChange = {
                    rating = it
                },

                totalPages = totalPages,
                onTotalPagesChange = {
                    totalPages = it
                },

                currentPage = currentPage,
                onCurrentPageChange = {
                    currentPage = it
                },

                coverUri = coverUri,
                onCoverUriChange = {
                    coverUri = it
                },
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .background(Surface, RoundedCornerShape(Dimens.cornerRadius))
                    .padding(Dimens.paddingMedium)
            ) {

                TextField(

                    value = searchQuery,

                    onValueChange = {
                        searchQuery = it
                    },

                    placeholder = {

                        Text(
                            stringResource(R.string.search_book)
                        )
                    },

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(20.dp),

                    colors = TextFieldDefaults.colors(

                        focusedContainerColor =
                            Color(0xFFF3EFE7),

                        unfocusedContainerColor =
                            Color(0xFFF3EFE7),

                        focusedIndicatorColor =
                            Color.Transparent,

                        unfocusedIndicatorColor =
                            Color.Transparent
                    )
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                BookGrid(

                    books = searchBooks,

                    emptyText =
                        stringResource(R.string.nothing_found),

                    onBookClick = { selectedBook ->

                        title = selectedBook.title
                        author = selectedBook.author
                        category = selectedBook.category

                        totalPages =
                            selectedBook.totalPages.toString()

                        currentPage =
                            selectedBook.currentPage.toString()

                        rating =
                            selectedBook.rating

                        status =
                            selectedBook.status

                        coverUri =
                            selectedBook.coverUri
                    }
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF6F1E9
)
@Composable
private fun AddBookScreenPreview() {

    AddBookScreenContent(

        onBackClick = {},

        onSaveClick = {}
    )
}