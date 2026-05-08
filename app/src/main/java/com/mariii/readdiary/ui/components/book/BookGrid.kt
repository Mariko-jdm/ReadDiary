package com.mariii.readdiary.ui.components.book

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.mariii.readdiary.domain.model.Book
import com.mariii.readdiary.ui.components.state.EmptyState
import com.mariii.readdiary.ui.theme.AppTypography
import com.mariii.readdiary.ui.theme.OnSurface
import com.mariii.readdiary.ui.theme.SurfaceVariant

@Composable
fun BookGrid(
    books: List<Book>,
    emptyText: String,
    onBookClick: (Int) -> Unit
) {

    if (books.isEmpty()) {

        EmptyState(
            text = emptyText,
            modifier = Modifier.fillMaxWidth()
        )

    } else {

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),

            verticalArrangement =
                androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp),

            horizontalArrangement =
                androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp),

            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp)
        ) {

            items(books) { book ->

                BookGridItem(
                    book = book,
                    onClick = {
                        onBookClick(book.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun BookGridItem(
    book: Book,
    onClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .clickable {
                onClick()
            }
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f)
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceVariant)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = book.title,
            style = AppTypography.bodyMedium,
            color = OnSurface,
            maxLines = 1
        )

        Text(
            text = book.author,
            style = AppTypography.bodySmall,
            color = OnSurface.copy(alpha = 0.7f),
            maxLines = 1
        )
    }
}