package com.mariii.readdiary.ui.components.book

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mariii.readdiary.R
import com.mariii.readdiary.domain.model.Book
import com.mariii.readdiary.domain.model.ReadingStatus
import com.mariii.readdiary.ui.theme.OnButtonLight
import com.mariii.readdiary.ui.theme.OnSurface
import com.mariii.readdiary.ui.theme.Primary
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun UpdateProgressDialog(
    book: Book,
    onDismiss: () -> Unit,
    onConfirm: (Int, ReadingStatus) -> Unit
) {

    var selectedPage by remember {
        mutableIntStateOf(book.currentPage)
    }
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = book.currentPage
    )
    val itemHeight = 40.dp
    LaunchedEffect(listState) {

        snapshotFlow {

            listState.firstVisibleItemIndex

        }.distinctUntilChanged()

            .collectLatest { index ->

                selectedPage = (index)
                    .coerceIn(0, book.totalPages)
            }
    }

    val flingBehavior = rememberSnapFlingBehavior(
        lazyListState = listState,
    )

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(stringResource(R.string.progress_reading))
        },
        text = {

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                LazyColumn(
                    state = listState,
                    flingBehavior = flingBehavior,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight * 3),
                    contentPadding = PaddingValues(vertical = itemHeight),
                    verticalArrangement = Arrangement.Center
                ) {

                    items((0..book.totalPages).toList()) { page ->

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(itemHeight),

                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = page.toString(),

                                color = OnSurface,

                                style =
                                    if (page == selectedPage) {
                                        MaterialTheme.typography.titleLarge
                                    } else {
                                        MaterialTheme.typography.bodyLarge
                                    }
                            )
                        }
                    }
                }

                Button(
                    onClick = {

                        onConfirm(
                            book.totalPages,
                            ReadingStatus.FINISHED
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = OnButtonLight
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(stringResource(R.string.finish_book))
                }
            }
        },

        confirmButton = {

            TextButton(
                onClick = {

                    val newStatus =
                        if (selectedPage >= book.totalPages) {
                            ReadingStatus.FINISHED
                        } else {
                            ReadingStatus.READING
                        }

                    onConfirm(
                        selectedPage,
                        newStatus
                    )
                }
            ) {

                Text(
                    stringResource(R.string.save),
                    color = OnSurface
                )
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {

                Text(
                    stringResource(R.string.cancel),
                    color = OnSurface
                )
            }
        }
    )
}