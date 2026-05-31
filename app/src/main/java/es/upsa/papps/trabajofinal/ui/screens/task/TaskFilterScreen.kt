package es.upsa.papps.trabajofinal.ui.screens.task

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import es.upsa.papps.trabajofinal.model.data.filter.TaskStatusFilter
import es.upsa.papps.trabajofinal.model.data.TaskFilters
import es.upsa.papps.trabajofinal.model.data.filter.TaskSubjectFilter
import es.upsa.papps.trabajofinal.model.data.filter.TaskTypeFilter
import es.upsa.papps.trabajofinal.ui.util.DatePickerField
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFilterScreen(
    navController: NavController,
    vm: MainViewModel,
) {
    val state by vm.uiState.collectAsState()
    val subjects = state.subjects
    val current = state.activeTaskFilters

    var expandedStatus by remember { mutableStateOf(false) }
    var expandedSubject by remember { mutableStateOf(false) }

    var status by remember { mutableStateOf(current.status) }
    var subjectId by remember { mutableStateOf(current.subject) }
    var taskType by remember { mutableStateOf(current.taskType) }
    var fromDate: LocalDate? by remember { mutableStateOf(null) }
    var toDate: LocalDate? by remember { mutableStateOf(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Filtros") })
        }
    ) { padding ->

        Column(
            Modifier
                .padding(16.dp)
                .padding(padding)
        ) {

            // ---------------- STATUS ----------------
            ExposedDropdownMenuBox(
                expanded = expandedStatus,
                onExpandedChange = { expandedStatus = it }
            ) {
                OutlinedTextField(
                    value = when (status) {
                        TaskStatusFilter.ALL -> "Todas"
                        TaskStatusFilter.COMPLETED -> "Completadas"
                        TaskStatusFilter.PENDING -> "Sin completar"
                    },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Estado") },
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                )

                ExposedDropdownMenu(
                    expanded = expandedStatus,
                    onDismissRequest = { expandedStatus = false }
                ) {
                    TaskStatusFilter.entries.forEach {
                        DropdownMenuItem(
                            text = { Text(when (it) {
                                TaskStatusFilter.ALL -> "Todas"
                                TaskStatusFilter.COMPLETED -> "Completadas"
                                TaskStatusFilter.PENDING -> "Sin completar"
                            }) },
                            onClick = {
                                status = it
                                expandedStatus = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // ---------------- SUBJECT ----------------
            ExposedDropdownMenuBox(
                expanded = expandedSubject,
                onExpandedChange = { expandedSubject = it }
            ) {
                OutlinedTextField(
                    value = when (val s = subjectId) {
                        TaskSubjectFilter.All -> "Todas las asignaturas"
                        TaskSubjectFilter.NoSubject -> "Sin asignatura"
                        is TaskSubjectFilter.Subject ->
                            subjects.find { it.id == s.subjectId }?.name ?: "Asignatura"
                    },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Asignatura") },
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                )

                ExposedDropdownMenu(
                    expanded = expandedSubject,
                    onDismissRequest = { expandedSubject = false }
                ) {

                    DropdownMenuItem(
                        text = { Text("Todas las asignaturas") },
                        onClick = {
                            subjectId = TaskSubjectFilter.All
                            expandedSubject = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Sin asignatura") },
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

            // ---------------- ONLY EXAMS ----------------
            Column {
                Text("Tipo de tarea")

                Spacer(Modifier.height(8.dp))

                Row {
                    FilterChip(
                        selected = taskType == TaskTypeFilter.ALL,
                        onClick = { taskType = TaskTypeFilter.ALL },
                        label = { Text("Todas") }
                    )

                    Spacer(Modifier.width(8.dp))

                    FilterChip(
                        selected = taskType == TaskTypeFilter.TASK,
                        onClick = { taskType = TaskTypeFilter.TASK },
                        label = { Text("Tareas") }
                    )

                    Spacer(Modifier.width(8.dp))

                    FilterChip(
                        selected = taskType == TaskTypeFilter.EXAM,
                        onClick = { taskType = TaskTypeFilter.EXAM },
                        label = { Text("Exámenes") }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // ---------------- DATE FROM ----------------
            DatePickerField(
                label = { Text("Desde") },
                value = fromDate,
                onValueChange = { fromDate = it },
            )

            Spacer(Modifier.height(12.dp))

            // ---------------- DATE TO ----------------
            DatePickerField(
                label = { Text("Hasta") },
                value = toDate,
                onValueChange = { toDate = it },
            )

            Spacer(Modifier.height(20.dp))

            // ---------------- APPLY ----------------
            Button(
                onClick = {

                    vm.updateFilters(
                        TaskFilters(
                            status = status,
                            subject = subjectId,
                            taskType = taskType,
                            fromDate = runCatching { fromDate }.getOrNull(),
                            toDate = runCatching { toDate }.getOrNull(),
                        )
                    )

                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Aplicar filtros")
            }
        }
    }
}