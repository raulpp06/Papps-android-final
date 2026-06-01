package es.upsa.papps.trabajofinal.ui.screens.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.upsa.papps.trabajofinal.R
import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.ui.util.DatePickerField
import es.upsa.papps.trabajofinal.ui.util.SubjectPickerField
import es.upsa.papps.trabajofinal.ui.util.TimePickerField
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun AbstractTaskForm(
    title: String,
    onTitleChange: (String) -> Unit,
    titleIsError: Boolean,
    subjects: List<Subject>,
    subjectId: Int?,
    onSubjectSelected: (Int?) -> Unit,
    subjectIsError: Boolean,
    dueDate: LocalDate?,
    onDueDateChange: (LocalDate?) -> Unit,
    dueDateIsError: Boolean,
    isExam: Boolean,
    onExamToggle: (Boolean) -> Unit,
    examRoom: String,
    onExamRoomChange: (String) -> Unit,
    examRoomIsError: Boolean,
    examHour: LocalTime?,
    onExamHourChange: (LocalTime?) -> Unit,
    examHourIsError: Boolean,
    gradePercent: String,
    onGradePercentChange: (String) -> Unit,
    gradePercentIsError: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier.padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            isError = titleIsError,
            supportingText = { if (titleIsError) Text(stringResource(R.string.ui_mandatory_field)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.ui_title)) },
        )

        Spacer(Modifier.height(12.dp))

        SubjectPickerField(
            subjects = subjects,
            subjectId = subjectId,
            isError = subjectIsError,
            supportingText = { if (subjectIsError) Text(stringResource(R.string.ui_mandatory_field)) },
            modifier = Modifier.fillMaxWidth(),
            onSubjectSelected = onSubjectSelected,
        )

        Spacer(Modifier.height(12.dp))

        DatePickerField(
            value = dueDate,
            onValueChange = onDueDateChange,
            isError = dueDateIsError,
            supportingText = { if (dueDateIsError) Text(stringResource(R.string.ui_mandatory_field)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.ui_date)) },
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
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

        if (isExam) {
            OutlinedTextField(
                value = examRoom,
                onValueChange = onExamRoomChange,
                isError = examRoomIsError,
                supportingText = { if (examRoomIsError) Text(stringResource(R.string.ui_mandatory_field)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.ui_room)) },
            )

            Spacer(Modifier.height(8.dp))

            TimePickerField(
                value = examHour,
                onValueChange = onExamHourChange,
                isError = examHourIsError,
                supportingText = { if (examHourIsError) Text(stringResource(R.string.ui_mandatory_field)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.ui_hour)) },
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = gradePercent,
                onValueChange = onGradePercentChange,
                isError = gradePercentIsError,
                supportingText = { if (gradePercentIsError) Text(stringResource(R.string.ui_mandatory_field)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.ui_grade_weight)) },
            )
        }
    }
}