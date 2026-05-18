package com.mariii.readdiary.ui.components.note

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mariii.readdiary.R
import com.mariii.readdiary.ui.theme.OnSurface

@Composable
fun AddNoteDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {

    var text by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(stringResource(R.string.new_note))
        },

        text = {

            OutlinedTextField(
                shape = RoundedCornerShape(20.dp),
                value = text,
                onValueChange = {
                    text = it
                },

                modifier = Modifier.fillMaxWidth(),

                placeholder = {
                    Text(stringResource(R.string.enter_text))
                }
            )
        },

        confirmButton = {

            TextButton(
                onClick = {

                    if (text.isNotBlank()) {
                        onConfirm(text)
                    }
                }
            ) {
                Text(stringResource(R.string.save), color = OnSurface)
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.cancel), color = OnSurface)
            }
        }
    )
}