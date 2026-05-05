package com.mariii.readdiary.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    background = Background,
    surface = Surface,
    onPrimary = Surface,
    onBackground = OnBackground,
    onSurface = OnSurface
)

@Composable
fun ReadDiaryTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = AppTypography,
        shapes = Shapes,
        content = content
    )
}