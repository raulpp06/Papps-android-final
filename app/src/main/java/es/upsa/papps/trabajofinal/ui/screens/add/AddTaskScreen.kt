package es.upsa.papps.trabajofinal.ui.screens.add

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.model.data.Task
import es.upsa.papps.trabajofinal.navigation.Routes
import es.upsa.papps.trabajofinal.viewmodel.AddTaskViewModel
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    navController: NavController,
    vm: MainViewModel,
    addVm: AddTaskViewModel = viewModel(),
) {

    val subjects = vm.uiState.collectAsState().value.subjects
    var expanded by remember { mutableStateOf(false) }

    val selectedSubject = subjects.find { it.id == addVm.subjectId }

    Column(Modifier.padding(16.dp)) {

        // TITLE
        OutlinedTextField(
            value = addVm.title,
            onValueChange = { addVm.title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // SUBJECT DROPDOWN
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedSubject?.name ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Asignatura") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                subjects.forEach { subject ->
                    DropdownMenuItem(
                        text = { Text(subject.name) },
                        onClick = {
                            addVm.subjectId = subject.id
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // DATE
        OutlinedTextField(
            value = addVm.dueDate,
            onValueChange = { addVm.dueDate = it },
            label = { Text("Fecha (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // TYPE SELECTOR (Task / Exam)
        Row(Modifier.fillMaxWidth()) {

            Button(
                onClick = { addVm.isExam = false },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!addVm.isExam)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text("Tarea")
            }

            Spacer(Modifier.width(8.dp))

            Button(
                onClick = { addVm.isExam = true },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (addVm.isExam)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text("Examen")
            }
        }

        Spacer(Modifier.height(16.dp))

        // EXAM FIELDS
        if (addVm.isExam) {

            OutlinedTextField(
                value = addVm.examRoom,
                onValueChange = { addVm.examRoom = it },
                label = { Text("Aula") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = addVm.examHour,
                onValueChange = { addVm.examHour = it },
                label = { Text("Hora") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = addVm.gradePercent,
                onValueChange = { addVm.gradePercent = it },
                label = { Text("Peso (0 - 1)") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(20.dp))

        // SAVE BUTTON
        Button(
            onClick = {

                val task = if (addVm.isExam) {
                    Exam(
                        id = vm.uiState.value.tasks.size + 1,
                        subjectId = addVm.subjectId!!,
                        title = addVm.title,
                        dueDate = addVm.dueDate,
                        isCompleted = false,
                        examRoom = addVm.examRoom,
                        examHour = addVm.examHour,
                        gradePercent = addVm.gradePercent.toFloat()
                    )
                } else {
                    Task(
                        id = vm.uiState.value.tasks.size + 1,
                        subjectId = addVm.subjectId!!,
                        title = addVm.title,
                        dueDate = addVm.dueDate,
                        isCompleted = false
                    )
                }

                vm.addTask(task)
                navController.navigate(Routes.TASKS)
            },
            enabled = addVm.isValid(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Añadir")
        }
    }
}