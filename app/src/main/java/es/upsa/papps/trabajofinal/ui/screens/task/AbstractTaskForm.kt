package es.upsa.papps.trabajofinal.ui.screens.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.upsa.papps.trabajofinal.model.data.Subject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbstractTaskForm(
    title: String,
    onTitleChange: (String) -> Unit,
    subjects: List<Subject>,
    subjectId: Int?,
    onSubjectSelected: (Int?) -> Unit,
    dueDate: String,
    onDueDateChange: (String) -> Unit,
    isExam: Boolean,
    onExamToggle: (Boolean) -> Unit,
    examRoom: String,
    onExamRoomChange: (String) -> Unit,
    examHour: String,
    onExamHourChange: (String) -> Unit,
    gradePercent: String,
    onGradePercentChange: (String) -> Unit,
    onSubmit: () -> Unit,
    submitText: String,
    enabled: Boolean,
) {
    var expanded by remember { mutableStateOf(false) }

    val selectedSubject = subjects.find { it.id == subjectId }

    Column(Modifier.padding(16.dp)) {
        // TITLE
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Título") },
        )

        Spacer(Modifier.height(12.dp))

        // SUBJECT
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
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
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

        Spacer(Modifier.height(12.dp))

        // DATE
        OutlinedTextField(
            value = dueDate,
            onValueChange = onDueDateChange,
            label = { Text("Fecha (YYYY-MM-DD)") },
        )

        Spacer(Modifier.height(16.dp))

        // TYPE TOGGLE
        Row {
            Button(
                onClick = { onExamToggle(false) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isExam) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    },
                ),
            ) {
                Text("Tarea")
            }

            Spacer(Modifier.width(8.dp))

            Button(
                onClick = { onExamToggle(true) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isExam) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    },
                ),
            ) {
                Text("Examen")
            }
        }

        Spacer(Modifier.height(16.dp))

        // EXAM FIELDS
        if (isExam) {
            OutlinedTextField(
                value = examRoom,
                onValueChange = onExamRoomChange,
                label = { Text("Aula") },
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = examHour,
                onValueChange = onExamHourChange,
                label = { Text("Hora") },
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = gradePercent,
                onValueChange = onGradePercentChange,
                label = { Text("Peso (0-1)") },
            )
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onSubmit,
            enabled = enabled,
        ) {
            Text(submitText)
        }
    }
}
