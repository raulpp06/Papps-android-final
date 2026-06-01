package es.upsa.papps.trabajofinal.ui.screens.subject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.upsa.papps.trabajofinal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbstractSubjectForm(
    pageTitle: String,
    name: String,
    onNameChange: (String) -> Unit,
    teacher: String,
    onTeacherChange: (String) -> Unit,
    credits: String,
    onCreditsChange: (String) -> Unit,
    onSubmit: () -> Unit,
    submitText: String,
    enabled: Boolean,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pageTitle) },
            )
        },
    ) { padding ->
        Column(
            Modifier
                .padding(16.dp)
                .padding(padding),
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text(stringResource(R.string.ui_name)) },
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = teacher,
                onValueChange = onTeacherChange,
                label = { Text(stringResource(R.string.ui_profesor)) },
            )

            Spacer(Modifier.height(8.dp))

            val showError = credits.isNotBlank() &&
                    (credits.toIntOrNull()?.let { it > 0 } != true)

            OutlinedTextField(
                value = credits,
                onValueChange = onCreditsChange,
                isError = showError,
                supportingText = {
                    if (showError) {
                        Text(stringResource(R.string.ui_error_positive_number))
                    }
                },
                label = { Text(stringResource(R.string.ui_credits)) },
            )

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = onSubmit,
                enabled = enabled,
            ) {
                Text(submitText)
            }
        }
    }
}
