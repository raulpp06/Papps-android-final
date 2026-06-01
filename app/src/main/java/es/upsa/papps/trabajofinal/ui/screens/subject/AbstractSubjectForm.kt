package es.upsa.papps.trabajofinal.ui.screens.subject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.upsa.papps.trabajofinal.R

@Composable
fun AbstractSubjectForm(
    name: String,
    onNameChange: (String) -> Unit,
    teacher: String,
    onTeacherChange: (String) -> Unit,
    credits: String,
    onCreditsChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.ui_name)) },
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = teacher,
            onValueChange = onTeacherChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.ui_profesor)) },
        )

        Spacer(Modifier.height(8.dp))

        val showError = credits.isNotBlank() &&
                (credits.toIntOrNull()?.let { it > 0 } != true)

        OutlinedTextField(
            value = credits,
            onValueChange = onCreditsChange,
            modifier = Modifier.fillMaxWidth(),
            isError = showError,
            supportingText = {
                if (showError) {
                    Text(stringResource(R.string.ui_error_positive_number))
                }
            },
            label = { Text(stringResource(R.string.ui_credits)) },
        )
    }
}
