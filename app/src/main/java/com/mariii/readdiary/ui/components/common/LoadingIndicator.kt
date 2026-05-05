package com.mariii.readdiary.ui.components.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mariii.readdiary.ui.theme.*
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Primary
        )
    }
}
// превью для проверки
@Preview(showBackground = true)
@Composable
fun LoadingIndicatorPreview() {
    ReadDiaryTheme {
        LoadingIndicator()
    }
}