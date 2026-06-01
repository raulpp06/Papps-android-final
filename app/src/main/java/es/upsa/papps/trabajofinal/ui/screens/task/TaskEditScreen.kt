package es.upsa.papps.trabajofinal.ui.screens.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.upsa.papps.trabajofinal.R
import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.model.data.Task
import es.upsa.papps.trabajofinal.navigation.Routes
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel
import es.upsa.papps.trabajofinal.viewmodel.TaskFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditScreen(
    navController: NavController,
    id: Int,
    vm: MainViewModel,
    type: String,
    editVm: TaskFormViewModel = viewModel(),
) {
    val state by vm.uiState.collectAsState()
    val task = when (type) {
        Routes.Subroutes.TASK -> state.tasks.find { it.id == id }
        Routes.Subroutes.EXAM -> state.exams.find { it.id == id }
        else -> return
    } ?: return
    val subjects = state.subjects

    LaunchedEffect(task) {
        editVm.load(task)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.ui_edit_task))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.ui_back),
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = {

                            val subjectId = editVm.subjectId

                            val updated = if (editVm.isExam) {
                                Exam(
                                    id = id,
                                    subjectId = subjectId!!, // nunca es null por el isValid
                                    title = editVm.title,
                                    dueDate = editVm.dueDate!!, // nunca es null por el isValid
                                    isCompleted = task.isCompleted,
                                    examRoom = editVm.examRoom,
                                    examHour = editVm.examHour!!, // nunca es null por el isValid
                                    gradePercent = editVm.gradePercent.toFloat()
                                )
                            } else {
                                Task(
                                    id = id,
                                    subjectId = subjectId,
                                    title = editVm.title,
                                    dueDate = editVm.dueDate!!, // nunca es null por el isValid
                                    isCompleted = task.isCompleted
                                )
                            }

                            vm.updateTaskBase(task, updated)

                            navController.navigate(Routes.TASKS) {
                                popUpTo(Routes.TASKS) { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        enabled = editVm.isValid(),
                    ) {
                        Text(stringResource(R.string.ui_save))
                    }
                },
            )
        },
    ) { padding ->
        AbstractTaskForm(
            title = editVm.title,
            onTitleChange = { editVm.title = it },
            titleIsError = editVm.title.isBlank(),

            subjects = subjects,
            subjectId = editVm.subjectId,
            onSubjectSelected = { editVm.subjectId = it },
            subjectIsError = editVm.isExam && editVm.subjectId == null,

            dueDate = editVm.dueDate,
            onDueDateChange = { editVm.dueDate = it },
            dueDateIsError = editVm.dueDate == null,

            isExam = editVm.isExam,
            onExamToggle = { editVm.isExam = it },

            examRoom = editVm.examRoom,
            onExamRoomChange = { editVm.examRoom = it },
            examRoomIsError = editVm.examRoom.isBlank(),

            examHour = editVm.examHour,
            onExamHourChange = { editVm.examHour = it },
            examHourIsError = editVm.examHour == null,

            gradePercent = editVm.gradePercent,
            onGradePercentChange = { editVm.gradePercent = it },
            gradePercentIsError = (editVm.gradePercent.toDoubleOrNull() ?: -1.0) !in 0.0..1.0,

            modifier = Modifier.padding(padding),
        )
    }
}