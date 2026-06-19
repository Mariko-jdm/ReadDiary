package com.mariii.readdiary.ui.components.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mariii.readdiary.ui.theme.AppTypography
import com.mariii.readdiary.ui.theme.Dimens
import com.mariii.readdiary.ui.theme.OnSurface
import com.mariii.readdiary.ui.theme.ReadDiaryTheme

@Composable
fun EmptyState(
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.paddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.AutoMirrored.Outlined.MenuBook,
            contentDescription = null,
            tint = OnSurface,
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = text,
            style = AppTypography.bodyMedium,
            color = OnSurface
        )
        }
    }


@Preview(showBackground = true)
@Composable
fun EmptyStatePreview() {
    ReadDiaryTheme {
        EmptyState(
            text = "у вас пока нет книг"
        )
    }
}