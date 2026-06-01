package es.upsa.papps.trabajofinal.ui.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.LocalTime

@Suppress("AssignedValueIsNeverRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerField(
    value: LocalTime?,
    onValueChange: (LocalTime?) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
) {
    val state = rememberTimePickerState()
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                label = label,
                value = value?.toString() ?: "",
                isError = isError,
                supportingText = supportingText,
                onValueChange = {},
                readOnly = true,
                modifier = modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        showDialog = true
                    },
                trailingIcon = {
                    if (value != null) {
                        IconButton(onClick = { onValueChange(null) }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Limpiar hora"
                            )
                        }
                    }
                }
            )
            if (value == null) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            showDialog = true
                        }
                )
            }
        }
    }

    if (showDialog) {
        TimePickerDialog(
            title = { Text("Selecciona la hora") },
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    onValueChange(LocalTime.of(state.hour, state.minute))
                    showDialog = false
                }) {
                    Text("OK")
                }
            },
        ) {
            TimePicker(state = state)
        }
    }
}