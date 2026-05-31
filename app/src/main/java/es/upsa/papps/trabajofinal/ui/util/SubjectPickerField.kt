package es.upsa.papps.trabajofinal.ui.util

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import es.upsa.papps.trabajofinal.model.data.Subject

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SubjectPickerField(
    subjects: List<Subject>,
    subjectId: Int?,
    onSubjectSelected: (Int?) -> Unit,
) {

    val selectedSubject = subjects.find { it.id == subjectId }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            value = selectedSubject?.name ?: "Sin asignatura",
            onValueChange = {},
            readOnly = true,
            label = { Text("Asignatura") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = { Text("Sin asignatura") },
                onClick = {
                    onSubjectSelected(null)
                    expanded = false
                }
            )
            subjects.forEach { subject ->
                DropdownMenuItem(
                    text = { Text(subject.name) },
                    onClick = {
                        onSubjectSelected(subject.id)
                        expanded = false
                    },
                )
            }
        }
    }
}