package es.upsa.papps.trabajofinal.ui.screens.task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.upsa.papps.trabajofinal.R
import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.model.data.Task
import es.upsa.papps.trabajofinal.navigation.Routes
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel
import es.upsa.papps.trabajofinal.viewmodel.TaskFormViewModel

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
    }?: return
    val subjects = state.subjects

    LaunchedEffect(task) {
        editVm.load(task)
    }

    AbstractTaskForm(
        title = editVm.title,
        onTitleChange = { editVm.title = it },

        subjects = subjects,
        subjectId = editVm.subjectId,
        onSubjectSelected = { editVm.subjectId = it },

        dueDate = editVm.dueDate,
        onDueDateChange = { editVm.dueDate = it },

        isExam = editVm.isExam,
        onExamToggle = { editVm.isExam = it },

        examRoom = editVm.examRoom,
        onExamRoomChange = { editVm.examRoom = it },

        examHour = editVm.examHour,
        onExamHourChange = { editVm.examHour = it },

        gradePercent = editVm.gradePercent,
        onGradePercentChange = { editVm.gradePercent = it },

        enabled = editVm.isValid(),
        submitText = stringResource(R.string.ui_save),

        onSubmit = {

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
        }
    )
}