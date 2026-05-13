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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mariii.readdiary.R
import com.mariii.readdiary.domain.model.Book
import com.mariii.readdiary.domain.model.ReadingNote
import com.mariii.readdiary.domain.model.ReadingStatus
import com.mariii.readdiary.ui.components.common.PrimaryButton
import com.mariii.readdiary.ui.components.note.AddNoteDialog
import com.mariii.readdiary.ui.theme.AppTypography
import com.mariii.readdiary.ui.theme.Background
import com.mariii.readdiary.ui.theme.Dimens
import com.mariii.readdiary.ui.theme.OnSurface
import com.mariii.readdiary.ui.theme.Primary
import com.mariii.readdiary.ui.theme.ReadDiaryTheme
import com.mariii.readdiary.ui.theme.Surface
import com.mariii.readdiary.ui.theme.SurfaceVariant
import com.mariii.readdiary.ui.viewmodel.BookViewModel

@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookId: Int,
    viewModel: BookViewModel
) {

    val books by viewModel.books.collectAsState()

    val book = books.find { it.id == bookId }

    if (book == null) {
        return
    }

    BookDetailsContent(book = book)
}

@Composable
private fun BookInfoSection(
    book: Book
) {

    val progress =
        if (book.totalPages > 0)
            (book.currentPage * 100) / book.totalPages
        else 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.cornerRadius))
            .background(Surface)
            .padding(Dimens.paddingMedium)
    ) {

        Row {

            Box(
                modifier = Modifier
                    .size(width = 120.dp, height = 190.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceVariant)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = book.title,
                    style = AppTypography.titleLarge,
                    color = OnSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = book.author,
                    style = AppTypography.bodyMedium,
                    color = OnSurface.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = book.category,
                    style = AppTypography.bodyMedium,
                    color = OnSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = book.rating.toString(),
                        style = AppTypography.bodyMedium
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text("★")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${book.currentPage} из ${book.totalPages} стр. ($progress%)",
                        style = AppTypography.bodyMedium,
                        color = OnSurface,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
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
                                    .rotate(180f)
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { },

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary.copy(alpha = 0.45f),
                            contentColor = OnSurface.copy(alpha = 0.8f)
                        ),

                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text("Завершить")
                    }
                }



            }
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
            .background(SurfaceVariant.copy(alpha = 0.5f))
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
private fun BookDetailsContent(
    book: Book
) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    Scaffold(
        containerColor = Background,

        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    showDialog.value = true
                },
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

                    Text("Заметка")
                }
            }
        },

        topBar = {

            TopAppBar(
                title = {
                    Text("Книга")
                },

                navigationIcon = {

                    IconButton(
                        onClick = { }
                    ) {
                        Icon(Icons.Default.Menu, null)
                    }
                },

                actions = {

                    IconButton(
                        onClick = { }
                    ) {
                        Icon(Icons.Default.Edit, null)
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
                BookInfoSection(book)
            }

            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(Dimens.cornerRadius))
                        .background(Surface)
                        .padding(Dimens.paddingMedium)
                ) {

                    Text(
                        text = "Заметки и цитаты",
                        style = AppTypography.titleMedium,
                        color = OnSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    book.notes.forEach { note ->

                        NoteCard(note)

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
                        }
        }
    }
    if (showDialog.value) {

        AddNoteDialog(

            onDismiss = {
                showDialog.value = false
            },

            onConfirm = { text ->

                // пока просто закрываем
                // позже подключим ViewModel

                showDialog.value = false
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookDetailsScreenPreview() {

    val fakeBook = Book(
        id = 1,
        title = "Ночь в Лиссабоне",
        author = "Эрих Мария Ремарк",
        category = "Роман",
        rating = 4,
        totalPages = 300,
        currentPage = 130,
        status = ReadingStatus.READING,
        notes = listOf(
            ReadingNote(
                id = 1,
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent ac bibendum turpis."
            ),
            ReadingNote(
                id = 2,
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
            )
        )
    )

    ReadDiaryTheme {

        BookDetailsContent(
            book = fakeBook
        )
    }
}