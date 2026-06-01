package es.upsa.papps.trabajofinal.ui.screens.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.upsa.papps.trabajofinal.R
import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.ui.util.SubjectPickerField
import java.time.LocalDate

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
    val showDateError =
        dueDate.isNotBlank() && runCatching { LocalDate.parse(dueDate) }.isFailure

    val showHourError =
        isExam &&
                examHour.isNotBlank() &&
                runCatching { java.time.LocalTime.parse(examHour) }.isFailure

    Column(Modifier.padding(16.dp)) {
        // TITLE
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text(stringResource(R.string.ui_title)) },
        )

        Spacer(Modifier.height(12.dp))

        // SUBJECT
        SubjectPickerField(
            subjects = subjects,
            subjectId = subjectId,
            onSubjectSelected = onSubjectSelected,
        )

        Spacer(Modifier.height(12.dp))

        // DATE
        OutlinedTextField(
            value = dueDate,
            onValueChange = onDueDateChange,
            label = { Text(stringResource(R.string.ui_date_format)) },
            isError = showDateError,
            supportingText = {
                if (showDateError) {
                    Text(stringResource(R.string.ui_invalid_date))
                }
            }
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
                Text(stringResource(R.string.ui_task))
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
                Text(stringResource(R.string.ui_exam))
            }
        }

        Spacer(Modifier.height(16.dp))

        // EXAM FIELDS
        if (isExam) {
            OutlinedTextField(
                value = examRoom,
                onValueChange = onExamRoomChange,
                label = { Text(stringResource(R.string.ui_room)) },
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = examHour,
                onValueChange = onExamHourChange,
                label = { Text(stringResource(R.string.ui_hour_format)) },
                isError = showHourError,
                supportingText = {
                    if (showHourError) {
                        Text(stringResource(R.string.ui_invalid_hour))
                    }
                }
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = gradePercent,
                onValueChange = onGradePercentChange,
                label = { Text(stringResource(R.string.ui_grade_weight)) },
            )
        }

        Spacer(Modifier.height(20.dp))

        val canSubmit = enabled && !showDateError && (!isExam || !showHourError)

        Button(
            onClick = onSubmit,
            enabled = canSubmit,
        ) {
            Text(submitText)
        }
    }
}