package com.mariii.readdiary.ui.components.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.mariii.readdiary.ui.theme.*
import androidx.compose.ui.unit.dp
import com.mariii.readdiary.ui.components.common.PrimaryButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.Alignment
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.res.painterResource
import com.mariii.readdiary.R


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
            .background(Background)
            .padding(Dimens.paddingMedium)
    ) {

        // обложка (на всю высоту)
        Box(
            modifier = Modifier
                  .width(120.dp)
//                .height(220.dp)
//                .fillMaxHeight()
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

            Spacer(modifier = Modifier.height(16.dp))

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

                // кнопка с иконкой
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

                // подчеркнутый текст
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