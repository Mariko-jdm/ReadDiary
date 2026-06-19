package com.mariii.readdiary.ui.components.book

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mariii.readdiary.R
import com.mariii.readdiary.domain.model.ReadingStatus
import com.mariii.readdiary.ui.components.rating.RatingBar
import com.mariii.readdiary.ui.theme.AppTypography
import com.mariii.readdiary.ui.theme.Dimens
import com.mariii.readdiary.ui.theme.OnSurface
import com.mariii.readdiary.ui.theme.Surface
import com.mariii.readdiary.ui.theme.SurfaceVariant

@Composable
fun EditableBookContent(

    title: String,
    onTitleChange: (String) -> Unit,

    author: String,
    onAuthorChange: (String) -> Unit,

    category: String,
    onCategoryChange: (String) -> Unit,

    status: ReadingStatus,
    onStatusChange: (ReadingStatus) -> Unit,

    rating: Int,
    onRatingChange: (Int) -> Unit,

    totalPages: String,
    onTotalPagesChange: (String) -> Unit,

    currentPage: String,
    onCurrentPageChange: (String) -> Unit,

    coverUri: String,
    onCoverUriChange: (String) -> Unit,

    editable: Boolean = true
) {

    var categoryExpanded by remember {
        mutableStateOf(false)
    }

    var statusExpanded by remember {
        mutableStateOf(false)
    }

    val categories = listOf(
        "Роман",
        "Фэнтези",
        "Психология",
        "Философия",
        "Саморазвитие",
        "Классика",
        "Другое"
    )

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->

        uri?.let {

            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            onCoverUriChange(it.toString())
        }
    }

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

        // ОБЛОЖКА

        Box(

            modifier = Modifier.clickable {

                if (editable) {
                    galleryLauncher.launch(arrayOf("image/*"))
                }
            }
        ) {

            if (coverUri.isNotBlank()) {

                AsyncImage(

                    model = coverUri,

                    contentDescription = null,

                    contentScale = ContentScale.Crop,

                    modifier = Modifier
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
        }

        Spacer(
            modifier = Modifier.width(
                Dimens.spacingMedium
            )
        )

        // ПРАВАЯ ЧАСТЬ

        Column(
            modifier = Modifier.weight(1f)
        ) {

            MinimalTextField(
                value = title,
                onValueChange = onTitleChange,
                placeholder = stringResource(R.string.book_name),
                textStyle = AppTypography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            MinimalTextField(
                value = author,
                onValueChange = onAuthorChange,
                placeholder = stringResource(R.string.author)
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // CATEGORY

            Box {

                DropdownRow(

                    text = category.ifBlank {
                        stringResource(R.string.category)
                    },

                    onClick = {
                        categoryExpanded = true
                    }
                )

                DropdownMenu(

                    expanded = categoryExpanded,

                    onDismissRequest = {
                        categoryExpanded = false
                    }
                ) {

                    categories.forEach { item ->

                        DropdownMenuItem(

                            text = {
                                Text(item)
                            },

                            onClick = {

                                onCategoryChange(item)

                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            // STATUS

            Box {

                DropdownRow(

                    text = statusToText(status),

                    onClick = {
                        statusExpanded = true
                    }
                )

                DropdownMenu(

                    expanded = statusExpanded,

                    onDismissRequest = {
                        statusExpanded = false
                    }
                ) {

                    ReadingStatus.entries.forEach { item ->

                        DropdownMenuItem(

                            text = {
                                Text(statusToText(item))
                            },

                            onClick = {

                                onStatusChange(item)

                                statusExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            RatingBar(
                rating = rating,
                onRatingChange = onRatingChange
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            InlineNumberField(
                label = stringResource(R.string.total_pages),
                value = totalPages,
                onValueChange = onTotalPagesChange
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            InlineNumberField(
                label = stringResource(R.string.current_page),
                value = currentPage,
                onValueChange = onCurrentPageChange
            )
        }
    }
}

@Composable
fun MinimalTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    textStyle: androidx.compose.ui.text.TextStyle = AppTypography.bodyMedium,
    enabled: Boolean = true,

) {

    BasicTextField(
        value = value,
        onValueChange = {
            if (enabled) {
                onValueChange(it)
            }
        },
        enabled = enabled,
        singleLine = true,
        textStyle = textStyle.copy(color = OnSurface),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth(),

        decorationBox = { innerTextField ->

            if (value.isEmpty()) {

                Text(
                    text = placeholder,
                    style = textStyle,
                    color = OnSurface,

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

        Spacer(
            modifier = Modifier.width(2.dp)
        )

        BasicTextField(

            value = value,

            onValueChange = {
                onValueChange(it)
            },

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
fun statusToText(
    status: ReadingStatus
): String {

    return when (status) {

        ReadingStatus.READING ->
            stringResource(R.string.status_reading)

        ReadingStatus.FINISHED ->
            stringResource(R.string.status_finished)

        ReadingStatus.PLANNED ->
            stringResource(R.string.status_planned)

        ReadingStatus.DROPPED ->
            stringResource(R.string.status_dropped)
    }
}

//@Preview(
//    showBackground = true,
//    backgroundColor = 0xFFF6F1E9
//)
//@Composable
//private fun EditableBookContentPreview() {
//
//    ReadDiaryTheme {
//
//        EditableBookContent(
//
//            title = "1984",
//
//            onTitleChange = {},
//
//            author = "George Orwell",
//
//            onAuthorChange = {},
//
//            category = "Классика",
//
//            onCategoryChange = {},
//
//            status = ReadingStatus.READING,
//
//            onStatusChange = {},
//
//            rating = 4,
//
//            onRatingChange = {},
//
//            totalPages = "340",
//
//            onTotalPagesChange = {},
//
//            currentPage = "120",
//
//            onCurrentPageChange = {},
//
//            coverUrl = ""
//        )
//    }
//}