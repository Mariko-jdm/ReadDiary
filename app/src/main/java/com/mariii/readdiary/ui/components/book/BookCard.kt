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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mariii.readdiary.ui.theme.AppTypography
import com.mariii.readdiary.ui.theme.Dimens
import com.mariii.readdiary.ui.theme.OnSurface
import com.mariii.readdiary.ui.theme.Surface
import com.mariii.readdiary.ui.theme.SurfaceVariant


@Composable
fun BookCard(
    title: String,
    author: String,
    coverUri: String = "",
    content: @Composable ColumnScope.() -> Unit
) {

    Row(
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

        if (coverUri.isNotBlank()) {

            AsyncImage(

                model = coverUri,

                contentDescription = null,

                contentScale = ContentScale.Crop,

                modifier = Modifier
                    .width(120.dp)
                    .size(
                        width = 100.dp,
                        height = 170.dp
                    )
                    .clip(
                        RoundedCornerShape(12.dp)
                    )
            )

        } else {

            Box(
                modifier = Modifier
                    .width(120.dp)
                    .size(
                        width = 100.dp,
                        height = 170.dp
                    )
                    .clip(
                        RoundedCornerShape(12.dp)
                    )
                    .background(SurfaceVariant)
            )
        }

        Spacer(
            modifier = Modifier.width(
                Dimens.spacingMedium
            )
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = title,
                style = AppTypography.titleLarge,
                color = OnSurface
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = author,
                style = AppTypography.bodyMedium,
                color = OnSurface.copy(alpha = 0.9f)
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            content()
        }
    }
}