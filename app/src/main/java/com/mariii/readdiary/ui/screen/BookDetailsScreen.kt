@file:OptIn(ExperimentalMaterial3Api::class)

package com.mariii.readdiary.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mariii.readdiary.R
import com.mariii.readdiary.domain.model.Book
import com.mariii.readdiary.domain.model.ReadingNote
import com.mariii.readdiary.domain.model.ReadingStatus
import com.mariii.readdiary.ui.components.book.BookCard
import com.mariii.readdiary.ui.components.book.EditableBookContent
import com.mariii.readdiary.ui.components.book.UpdateProgressDialog
import com.mariii.readdiary.ui.components.book.statusToText
import com.mariii.readdiary.ui.components.common.PrimaryButton
import com.mariii.readdiary.ui.components.note.AddNoteDialog
import com.mariii.readdiary.ui.theme.AppTypography
import com.mariii.readdiary.ui.theme.Background
import com.mariii.readdiary.ui.theme.Dimens
import com.mariii.readdiary.ui.theme.OnSurface
import com.mariii.readdiary.ui.theme.Primary
import com.mariii.readdiary.ui.theme.Surface
import com.mariii.readdiary.ui.theme.SurfaceVariant
import com.mariii.readdiary.ui.viewmodel.BookViewModel

@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookId: Int,
    viewModel: BookViewModel,
) {

    val books by viewModel.books.collectAsState()

    val book = books.find {
        it.id == bookId
    } ?: return

    var selectedBookForProgress by remember {
        mutableStateOf<Book?>(null)
    }

    var showAddNoteDialog by remember {
        mutableStateOf(false)
    }

    var isEditing by remember {
        mutableStateOf(false)
    }

    var editedTitle by remember {
        mutableStateOf(book.title)
    }

    var editedAuthor by remember {
        mutableStateOf(book.author)
    }

    var editedCategory by remember {
        mutableStateOf(book.category)
    }

    var editedStatus by remember {
        mutableStateOf(book.status)
    }

    var editedRating by remember {
        mutableIntStateOf(book.rating)
    }

    var editedTotalPages by remember {
        mutableStateOf(book.totalPages.toString())
    }

    var editedCurrentPage by remember {
        mutableStateOf(book.currentPage.toString())
    }

    var editedCoverUri by remember {
        mutableStateOf(book.coverUri)
    }

    BookDetailsContent(

        book = book,

        editedTitle = editedTitle,
        onTitleChange = {
            editedTitle = it
        },

        editedAuthor = editedAuthor,
        onAuthorChange = {
            editedAuthor = it
        },

        editedCategory = editedCategory,
        onCategoryChange = {
            editedCategory = it
        },

        editedStatus = editedStatus,
        onStatusChange = {
            editedStatus = it
        },

        editedRating = editedRating,
        onRatingChange = {
            editedRating = it
        },

        editedTotalPages = editedTotalPages,
        onTotalPagesChange = {
            editedTotalPages = it
        },

        editedCurrentPage = editedCurrentPage,
        onCurrentPageChange = {
            editedCurrentPage = it
        },

        coverUri = editedCoverUri,
        onCoverUriChange = {
            editedCoverUri = it
        },

        isEditing = isEditing,

        onEditClick = {

            if (isEditing) {

                viewModel.updateBook(

                    book.copy(
                        title = editedTitle,
                        author = editedAuthor,
                        category = editedCategory,
                        status = editedStatus,
                        rating = editedRating,

                        totalPages =
                            editedTotalPages.toIntOrNull() ?: 0,

                        currentPage =
                            editedCurrentPage.toIntOrNull() ?: 0,

                        coverUri = editedCoverUri
                    )
                )
            }

            isEditing = !isEditing
        },

        onBackClick = {
            navController.popBackStack()
        },

        onContinueClick = {
            selectedBookForProgress = it
        },

        onAddNoteClick = {
            showAddNoteDialog = true
        },

        onFinishBookClick = {

            viewModel.updateReadingProgress(
                book = it,
                newPage = it.totalPages,
                newStatus = ReadingStatus.FINISHED
            )
        }
    )

    selectedBookForProgress?.let { currentBook ->

        UpdateProgressDialog(

            book = currentBook,

            onDismiss = {
                selectedBookForProgress = null
            },

            onConfirm = { newPage, newStatus ->

                viewModel.updateReadingProgress(
                    book = currentBook,
                    newPage = newPage,
                    newStatus = newStatus
                )

                selectedBookForProgress = null
            }
        )
    }

    if (showAddNoteDialog) {

        AddNoteDialog(

            onDismiss = {
                showAddNoteDialog = false
            },

            onConfirm = {

                showAddNoteDialog = false
            }
        )
    }
}

@Composable
private fun BookDetailsContent(

    book: Book,

    editedTitle: String,
    onTitleChange: (String) -> Unit,

    editedAuthor: String,
    onAuthorChange: (String) -> Unit,

    editedCategory: String,
    onCategoryChange: (String) -> Unit,

    editedStatus: ReadingStatus,
    onStatusChange: (ReadingStatus) -> Unit,

    editedRating: Int,
    onRatingChange: (Int) -> Unit,

    editedTotalPages: String,
    onTotalPagesChange: (String) -> Unit,

    editedCurrentPage: String,
    onCurrentPageChange: (String) -> Unit,

    coverUri: String,
    onCoverUriChange: (String) -> Unit,

    isEditing: Boolean,
    onEditClick: () -> Unit,

    onBackClick: () -> Unit,
    onContinueClick: (Book) -> Unit,
    onAddNoteClick: () -> Unit,
    onFinishBookClick: (Book) -> Unit
) {

    Scaffold(

        containerColor = Background,

        floatingActionButton = {

            FloatingActionButton(
                onClick = onAddNoteClick,
                containerColor = Primary,
                contentColor = OnSurface
            ) {

                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(stringResource(R.string.note))
                }
            }
        },

        topBar = {

            TopAppBar(

                title = {
                    Text(stringResource(R.string.book))
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
                        onClick = onEditClick
                    ) {

                        Icon(
                            imageVector =
                                if (isEditing) {
                                    Icons.Default.Check
                                } else {
                                    Icons.Default.Edit
                                },
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

        LazyColumn(

            modifier = Modifier
                .padding(padding)
                .padding(Dimens.paddingMedium),

            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {

                if (isEditing) {

                    EditableBookContent(

                        title = editedTitle,
                        onTitleChange = onTitleChange,

                        author = editedAuthor,
                        onAuthorChange = onAuthorChange,

                        category = editedCategory,
                        onCategoryChange = onCategoryChange,

                        status = editedStatus,
                        onStatusChange = onStatusChange,

                        rating = editedRating,
                        onRatingChange = onRatingChange,

                        totalPages = editedTotalPages,
                        onTotalPagesChange = onTotalPagesChange,

                        currentPage = editedCurrentPage,
                        onCurrentPageChange = onCurrentPageChange,

                        coverUri = coverUri,
                        onCoverUriChange = onCoverUriChange,

                    )

                } else {

                    BookCard(
                        title = editedTitle,
                        author = editedAuthor,
                        coverUri = coverUri
                    ) {

                        BookInfoActions(
                            book = book,
                            onContinueClick = onContinueClick,
                            onFinishBookClick = onFinishBookClick
                        )
                    }
                }
            }

            item {

                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                Dimens.cornerRadius
                            )
                        )
                        .background(Surface)
                        .padding(Dimens.paddingMedium)
                ) {

                    Text(
                        text = stringResource(R.string.note_and_citation),
                        style = AppTypography.titleMedium,
                        color = OnSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (book.notes.isEmpty()) {

                        Text(
                            text = stringResource(R.string.no_notes_yet),
                            style = AppTypography.bodyMedium,
                            color = OnSurface.copy(alpha = 0.9f),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                    } else {

                        book.notes.forEach { note ->

                            NoteCard(note)

                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BookInfoActions(
    book: Book,
    onContinueClick: (Book) -> Unit,
    onFinishBookClick: (Book) -> Unit
) {

    val progress =
        if (book.totalPages > 0) {
            (book.currentPage * 100) / book.totalPages
        } else {
            0
        }

    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            BookInfoChip(
                text = book.category
            )

            BookInfoChip(
                text = statusToText(book.status)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "${book.currentPage} из ${book.totalPages} стр. ($progress%)",
            style = AppTypography.bodyMedium,
            color = OnSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        PrimaryButton(

            text = stringResource(R.string.cont),

            onClick = {
                onContinueClick(book)
            },

            leadingIcon = {

                Icon(
                    painter = painterResource(R.drawable.triangle_play),
                    contentDescription = null,
                    tint = OnSurface,

                    modifier = Modifier
                        .size(20.dp)
                        .rotate(180f)
                )
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(

            onClick = {
                onFinishBookClick(book)
            },

            colors = ButtonDefaults.buttonColors(
                containerColor = Primary.copy(alpha = 0.85f),
                contentColor = OnSurface.copy(alpha = 0.9f)
            ),

            modifier = Modifier.fillMaxWidth()
        ) {

            Icon(
                Icons.Default.Check,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text("Завершить")
        }
    }
}

@Composable
private fun NoteCard(
    note: ReadingNote
) {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceVariant.copy(alpha = 0.9f))
            .padding(16.dp)
    ) {

        Text(
            text = note.text,
            style = AppTypography.bodyMedium,
            color = OnSurface
        )
    }
}

@Composable
private fun BookInfoChip(
    text: String
) {

    Box(

        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(
                SurfaceVariant.copy(alpha = 0.9f)
            )
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
    ) {

        Text(
            text = text,
            style = AppTypography.bodySmall,
            color = OnSurface
        )
    }
}

//@Preview(
//    showBackground = true,
//    backgroundColor = 0xFFF6F1E9
//)
//@Composable
//private fun BookDetailsContentPreview() {
//
//    ReadDiaryTheme {
//
//        BookDetailsContent(
//
//            book = Book(
//                id = 1,
//                title = "1984",
//                author = "George Orwell",
//                category = "Классика",
//                status = ReadingStatus.READING,
//                rating = 4,
//                totalPages = 340,
//                currentPage = 120,
//                notes = listOf(
//                    ReadingNote(1,
//                        "Свобода — это возможность сказать, что дважды два — четыре."
//                    )
//                )
//            ),
//
//            editedTitle = "1984",
//            onTitleChange = {},
//
//            editedAuthor = "George Orwell",
//            onAuthorChange = {},
//
//            editedCategory = "Классика",
//            onCategoryChange = {},
//
//            editedStatus = ReadingStatus.READING,
//            onStatusChange = {},
//
//            editedRating = 4,
//            onRatingChange = {},
//
//            editedTotalPages = "340",
//            onTotalPagesChange = {},
//
//            editedCurrentPage = "120",
//            onCurrentPageChange = {},
//
//            coverUrl = "",
//
//            isEditing = false,
//            onEditClick = {},
//
//            onBackClick = {},
//            onContinueClick = {},
//            onAddNoteClick = {},
//            onFinishBookClick = {}
//        )
//    }
//}
//
//@Preview(
//    showBackground = true,
//    backgroundColor = 0xFFF6F1E9
//)
//@Composable
//private fun BookDetailsContentEditingPreview() {
//
//    ReadDiaryTheme {
//
//        BookDetailsContent(
//
//            book = Book(
//                id = 1,
//                title = "1984",
//                author = "George Orwell",
//                category = "Классика",
//                status = ReadingStatus.READING,
//                rating = 4,
//                totalPages = 340,
//                currentPage = 120,
//                notes = emptyList()
//            ),
//
//            editedTitle = "1984",
//            onTitleChange = {},
//
//            editedAuthor = "George Orwell",
//            onAuthorChange = {},
//
//            editedCategory = "Классика",
//            onCategoryChange = {},
//
//            editedStatus = ReadingStatus.READING,
//            onStatusChange = {},
//
//            editedRating = 4,
//            onRatingChange = {},
//
//            editedTotalPages = "340",
//            onTotalPagesChange = {},
//
//            editedCurrentPage = "120",
//            onCurrentPageChange = {},
//
//            isEditing = true,
//            onEditClick = {},
//
//            onBackClick = {},
//            onContinueClick = {},
//            onAddNoteClick = {},
//            onFinishBookClick = {}
//        )
//    }
//}