package com.mariii.readdiary.ui.components.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mariii.readdiary.R
import com.mariii.readdiary.ui.components.common.PrimaryButton
import com.mariii.readdiary.ui.theme.AppTypography
import com.mariii.readdiary.ui.theme.Dimens
import com.mariii.readdiary.ui.theme.OnSurface
import com.mariii.readdiary.ui.theme.ReadDiaryTheme
import com.mariii.readdiary.ui.theme.Surface
import com.mariii.readdiary.ui.theme.SurfaceVariant


@Composable
fun BookCard(
    title: String,
    author: String,
    content: @Composable ColumnScope.() -> Unit
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
                .width(120.dp)
                .size(100.dp, 170.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceVariant)
        )

        Spacer(modifier = Modifier.width(Dimens.spacingMedium))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = title,
                style = AppTypography.titleLarge,
                color = OnSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = author,
                style = AppTypography.bodyMedium,
                color = OnSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookCardHomePreview() {
    ReadDiaryTheme {
        BookCard(
            title = "Название книги",
            author = "Автор"
        ) {

            // центрируем блок
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "130 из 300 стр. (35%)",
                    style = AppTypography.bodyMedium,
                    color = OnSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                // кнопка продолжения чтения
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
                                .rotate(180f) // поворот
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // кнопка добавления заметки
                Text(
                    text = "Добавить заметку",
                    style = AppTypography.bodyMedium.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    color = OnSurface
                )
            }
        }
    }
}