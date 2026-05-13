package com.mariii.readdiary.ui.components.note

import androidx.compose.foundation.layout.fillMaxWidth
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
            Text("Новая заметка")
        },

        text = {

            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                },

                modifier = Modifier.fillMaxWidth(),

                placeholder = {
                    Text("Введите текст заметки")
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

                Text("Сохранить")
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {

                Text("Отмена")
            }
        }
    )
}