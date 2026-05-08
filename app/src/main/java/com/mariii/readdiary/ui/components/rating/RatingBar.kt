package com.mariii.readdiary.ui.components.rating

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mariii.readdiary.ui.theme.OnBackground

@Composable
fun RatingBar(
    rating: Int,
    onRatingChange: (Int) -> Unit
) {

    Row {

        repeat(5) { index ->

            val starIndex = index + 1

            Icon(
                imageVector = if (starIndex <= rating) {
                    Icons.Default.Star
                } else {
                    Icons.Outlined.StarOutline
                },
                contentDescription = null,
                tint = OnBackground,
                modifier = Modifier.clickable {
                    onRatingChange(starIndex)
                }
            )
        }
    }
}