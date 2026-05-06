package com.mariii.readdiary.ui.components.state

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mariii.readdiary.ui.theme.*

@Composable
fun EmptyState(
    text: String,
    modifier: Modifier = Modifier,
    buttonText: String? = null,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.paddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Outlined.MenuBook,
            contentDescription = null,
            tint = OnSurface.copy(alpha = 0.4f),
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = text,
            style = AppTypography.bodyMedium,
            color = OnSurface.copy(alpha = 0.7f)
        )

        //  опциональная кнопка
        if (buttonText != null && onClick != null) {

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onClick) {
                Text(
                    text = buttonText,
                    color = Primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyStatePreview() {
    ReadDiaryTheme {
        EmptyState(
            text = "у вас пока нет книг",
            buttonText = "добавить книгу",
            onClick = {}
        )
    }
}