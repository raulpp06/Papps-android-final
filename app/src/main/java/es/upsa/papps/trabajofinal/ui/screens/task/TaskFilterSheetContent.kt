package es.upsa.papps.trabajofinal.ui.screens.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.upsa.papps.trabajofinal.R
import es.upsa.papps.trabajofinal.model.data.TaskFilters
import es.upsa.papps.trabajofinal.model.data.filter.TaskStatusFilter
import es.upsa.papps.trabajofinal.model.data.filter.TaskSubjectFilter
import es.upsa.papps.trabajofinal.model.data.filter.TaskTypeFilter
import es.upsa.papps.trabajofinal.ui.util.DatePickerField
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFilterSheetContent(
    vm: MainViewModel,
    onApply: () -> Unit,
) {
    val state by vm.uiState.collectAsState()
    val subjects = state.subjects
    val current = state.activeTaskFilters

    var expandedStatus by remember { mutableStateOf(false) }
    var expandedSubject by remember { mutableStateOf(false) }

    var status by remember { mutableStateOf(current.status) }
    var subjectId by remember { mutableStateOf(current.subject) }
    var taskType by remember { mutableStateOf(current.taskType) }
    var fromDate: LocalDate? by remember { mutableStateOf(current.fromDate) }
    var toDate: LocalDate? by remember { mutableStateOf(current.toDate) }

    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 32.dp)
    ) {
        Text(
            text = stringResource(R.string.ui_filters),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expandedStatus,
            onExpandedChange = { expandedStatus = it }
        ) {
            OutlinedTextField(
                value = when (status) {
                    TaskStatusFilter.ALL -> stringResource(R.string.ui_status_all)
                    TaskStatusFilter.COMPLETED -> stringResource(R.string.ui_status_completed)
                    TaskStatusFilter.PENDING -> stringResource(R.string.ui_status_pending)
                },
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.ui_status)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(
                expanded = expandedStatus,
                onDismissRequest = { expandedStatus = false }
            ) {
                TaskStatusFilter.entries.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(
                                when (it) {
                                    TaskStatusFilter.ALL -> stringResource(R.string.ui_status_all)
                                    TaskStatusFilter.COMPLETED -> stringResource(R.string.ui_status_completed)
                                    TaskStatusFilter.PENDING -> stringResource(R.string.ui_status_pending)
                                }
                            )
                        },
                        onClick = {
                            status = it
                            expandedStatus = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = expandedSubject,
            onExpandedChange = { expandedSubject = it }
        ) {
            OutlinedTextField(
                value = when (val s = subjectId) {
                    TaskSubjectFilter.All -> stringResource(R.string.ui_all_subjects)
                    TaskSubjectFilter.NoSubject -> stringResource(R.string.ui_no_subject)
                    is TaskSubjectFilter.Subject ->
                        subjects.find { it.id == s.subjectId }?.name
                            ?: stringResource(R.string.ui_subject_not_found)
                },
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.ui_subjects)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(
                expanded = expandedSubject,
                onDismissRequest = { expandedSubject = false }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.ui_all_subjects)) },
                    onClick = {
                        subjectId = TaskSubjectFilter.All
                        expandedSubject = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.ui_no_subject)) },
                    onClick = {
                        subjectId = TaskSubjectFilter.NoSubject
                        expandedSubject = false
                    }
                )
                subjects.forEach { subject ->
                    DropdownMenuItem(
                        text = { Text(subject.name) },
                        onClick = {
                            subjectId = TaskSubjectFilter.Subject(subject.id)
                            expandedSubject = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Text(stringResource(R.string.ui_task_type))
        Spacer(Modifier.height(8.dp))
        Row {
            FilterChip(
                selected = taskType == TaskTypeFilter.ALL,
                onClick = { taskType = TaskTypeFilter.ALL },
                label = { Text(stringResource(R.string.ui_status_all)) }
            )
            Spacer(Modifier.width(8.dp))
            FilterChip(
                selected = taskType == TaskTypeFilter.TASK,
                onClick = { taskType = TaskTypeFilter.TASK },
                label = { Text(stringResource(R.string.ui_type_tasks)) }
            )
            Spacer(Modifier.width(8.dp))
            FilterChip(
                selected = taskType == TaskTypeFilter.EXAM,
                onClick = { taskType = TaskTypeFilter.EXAM },
                label = { Text(stringResource(R.string.ui_type_exams)) }
            )
        }

        Spacer(Modifier.height(12.dp))

        DatePickerField(
            label = { Text(stringResource(R.string.ui_date_from)) },
            value = fromDate,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { fromDate = it },
        )

        Spacer(Modifier.height(12.dp))

        DatePickerField(
            label = { Text(stringResource(R.string.ui_date_to)) },
            value = toDate,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { toDate = it },
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                vm.updateFilters(
                    TaskFilters(
                        status = status,
                        subject = subjectId,
                        taskType = taskType,
                        fromDate = fromDate,
                        toDate = toDate,
                    )
                )
                onApply()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.ui_apply_filters))
        }
    }
}