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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mariii.readdiary.data.repository.FakeBookRepository
import com.mariii.readdiary.domain.model.ReadingStatus
import com.mariii.readdiary.ui.components.book.BookGrid
import com.mariii.readdiary.ui.components.book.EditableBookCard
import com.mariii.readdiary.ui.theme.Background
import com.mariii.readdiary.ui.theme.Dimens
import com.mariii.readdiary.ui.theme.ReadDiaryTheme
import com.mariii.readdiary.ui.theme.Surface


@Composable
fun AddBookScreen(
    navController: NavController,
    //viewModel: BookViewModel = viewModel()
) {


    AddBookScreenContent(
        onBackClick = {
            navController.popBackStack()
        },
        onSaveClick = {
            navController.popBackStack()
        }
    )
}

@Composable
fun AddBookScreenContent(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    val searchBooks = FakeBookRepository.getBooks()
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    var totalPages by remember { mutableStateOf("") }
    var currentPage by remember { mutableStateOf("0") }

    var rating by remember { mutableIntStateOf(0) }

    var status by remember {
        mutableStateOf(ReadingStatus.PLANNED)
    }

    var searchQuery by remember {
        mutableStateOf("")
    }

    Scaffold(
        containerColor = Background,

        topBar = {

            TopAppBar(

                title = {
                    Text("Новая книга")
                },

                navigationIcon = {

                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },

                actions = {

                    IconButton(
                        onClick = onSaveClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
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

            EditableBookCard(
                title = title,
                onTitleChange = { title = it },

                author = author,
                onAuthorChange = { author = it },

                category = category,
                onCategoryClick = {},

                status = status,
                onStatusClick = {},

                rating = rating,
                onRatingChange = { rating = it },

                totalPages = totalPages,
                onTotalPagesChange = { totalPages = it },

                currentPage = currentPage,
                onCurrentPageChange = { currentPage = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Dimens.cornerRadius))
                    .background(Surface)
                    .padding(Dimens.paddingMedium)
            ) {

                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text("Поиск книги в базе")
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

                Spacer(modifier = Modifier.height(16.dp))

                BookGrid(
                    books = searchBooks,
                    emptyText = "книги не найдены",
                    onBookClick = { }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddBookScreenPreview() {

    ReadDiaryTheme {

        AddBookScreenContent(
            onBackClick = {},
            onSaveClick = {}
        )
    }
}