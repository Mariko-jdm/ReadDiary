package com.mariii.readdiary.ui.components.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mariii.readdiary.domain.model.ReadingStatus
import com.mariii.readdiary.ui.components.rating.RatingBar
import com.mariii.readdiary.ui.theme.AppTypography
import com.mariii.readdiary.ui.theme.Dimens
import com.mariii.readdiary.ui.theme.OnSurface
import com.mariii.readdiary.ui.theme.ReadDiaryTheme
import com.mariii.readdiary.ui.theme.Surface
import com.mariii.readdiary.ui.theme.SurfaceVariant

@Composable
fun EditableBookCard(
    title: String,
    onTitleChange: (String) -> Unit,

    author: String,
    onAuthorChange: (String) -> Unit,

    category: String,
    onCategoryClick: () -> Unit,

    status: ReadingStatus,
    onStatusClick: () -> Unit,

    rating: Int,
    onRatingChange: (Int) -> Unit,

    totalPages: String,
    onTotalPagesChange: (String) -> Unit,

    currentPage: String,
    onCurrentPageChange: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.cornerRadius))
            .background(Surface)
            .padding(Dimens.paddingMedium)
    ) {

        // обложка
        Box(
            modifier = Modifier
                .size(width = 100.dp, height = 170.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceVariant)
        )

        Spacer(modifier = Modifier.width(Dimens.spacingMedium))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            // название
            MinimalTextField(
                value = title,
                onValueChange = onTitleChange,
                placeholder = "Название книги",
                textStyle = AppTypography.titleLarge
            )

            Spacer(modifier = Modifier.height(2.dp))

            // автор
            MinimalTextField(
                value = author,
                onValueChange = onAuthorChange,
                placeholder = "Автор"
            )

            Spacer(modifier = Modifier.height(6.dp))

            // категория
            DropdownRow(
                text = category.ifBlank {
                    "Категория"
                },
                onClick = onCategoryClick
            )

            Spacer(modifier = Modifier.height(2.dp))

            // статус
            DropdownRow(
                text = statusToText(status),
                onClick = onStatusClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            // рейтинг
            RatingBar(
                rating = rating,
                onRatingChange = onRatingChange
            )

            Spacer(modifier = Modifier.height(10.dp))

            // страницы
            InlineNumberField(
                label = "Всего страниц:",
                value = totalPages,
                onValueChange = onTotalPagesChange
            )

            Spacer(modifier = Modifier.height(4.dp))

            InlineNumberField(
                label = "Текущая страница:",
                value = currentPage,
                onValueChange = onCurrentPageChange
            )
        }
    }
}

@Composable
private fun MinimalTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    textStyle: androidx.compose.ui.text.TextStyle = AppTypography.bodyMedium
) {

    BasicTextField(
        value = value,
        onValueChange = onValueChange,

        singleLine = true,

        textStyle = textStyle.copy(
            color = OnSurface
        ),

        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),

        modifier = Modifier.fillMaxWidth(),

        decorationBox = { innerTextField ->

            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    style = textStyle,
                    color = OnSurface.copy(alpha = 0.5f)
                )
            }

            innerTextField()
        }
    )
}


@Composable
private fun InlineNumberField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = label,
            style = AppTypography.bodyMedium,
            color = OnSurface
        )

        Spacer(modifier = Modifier.width(2.dp))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,

            singleLine = true,

            textStyle = AppTypography.bodyMedium.copy(
                color = OnSurface
            ),

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),

            modifier = Modifier.width(40.dp),

            decorationBox = { innerTextField ->
                innerTextField()
            }
        )
    }
}

@Composable
private fun DropdownRow(
    text: String,
    onClick: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = text,
            style = AppTypography.bodyMedium,
            color = OnSurface
        )

        Spacer(modifier = Modifier.width(2.dp))

        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null
        )
    }
}

private fun statusToText(status: ReadingStatus): String {

    return when(status) {
        ReadingStatus.READING -> "Сейчас читаю"
        ReadingStatus.FINISHED -> "Завершена"
        ReadingStatus.PLANNED -> "В планах"
        ReadingStatus.DROPPED -> "Отложена"
    }
}

@Preview(showBackground = true)
@Composable
private fun EditableBookCardPreview() {

    ReadDiaryTheme {

        EditableBookCard(
            title = "Ночь в Лиссабоне",
            onTitleChange = {},

            author = "Эрих Мария Ремарк",
            onAuthorChange = {},

            category = "Роман",
            onCategoryClick = {},

            status = ReadingStatus.READING,
            onStatusClick = {},

            rating = 4,
            onRatingChange = {},

            totalPages = "328",
            onTotalPagesChange = {},

            currentPage = "120",
            onCurrentPageChange = {}
        )
    }
}