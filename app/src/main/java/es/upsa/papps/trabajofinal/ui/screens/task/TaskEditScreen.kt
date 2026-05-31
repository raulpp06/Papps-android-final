package es.upsa.papps.trabajofinal.ui.screens.task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.model.data.Task
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel
import es.upsa.papps.trabajofinal.viewmodel.TaskFormViewModel

@Composable
fun TaskEditScreen(
    navController: NavController,
    id: Int,
    vm: MainViewModel,
    editVm: TaskFormViewModel = viewModel(),
) {
    val state by vm.uiState.collectAsState()
    val task = state.tasks.find { it.id == id } ?: return
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

        dueDate = editVm.dueDateText,
        onDueDateChange = { editVm.dueDateText = it },

        isExam = editVm.isExam,
        onExamToggle = { editVm.isExam = it },

        examRoom = editVm.examRoom,
        onExamRoomChange = { editVm.examRoom = it },

        examHour = editVm.examHourText,
        onExamHourChange = { editVm.examHourText = it },

        gradePercent = editVm.gradePercent,
        onGradePercentChange = { editVm.gradePercent = it },

        enabled = editVm.isValid(),
        submitText = "Guardar",

        onSubmit = {

            val subjectId = editVm.subjectId

            val updated = if (editVm.isExam) {
                Exam(
                    id = id,
                    subjectId = subjectId!!, // nunca es null por el isValid
                    title = editVm.title,
                    dueDate = editVm.dueDate!!, // nunca es null por el isValid
                    isCompleted = (task as? Exam)?.isCompleted ?: false,
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

            vm.modifyTask(id, updated)
            navController.popBackStack()
        }
    )
}