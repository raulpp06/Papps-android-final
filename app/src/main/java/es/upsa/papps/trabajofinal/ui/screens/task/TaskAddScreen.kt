package es.upsa.papps.trabajofinal.ui.screens.task

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
fun TaskAddScreen(
    navController: NavController,
    vm: MainViewModel,
    addVm: TaskFormViewModel = viewModel()
) {
    val subjects = vm.uiState.collectAsState().value.subjects

    AbstractTaskForm(
        title = addVm.title,
        onTitleChange = { addVm.title = it },

        subjects = subjects,
        subjectId = addVm.subjectId,
        onSubjectSelected = { addVm.subjectId = it },

        dueDate = addVm.dueDate,
        onDueDateChange = { addVm.dueDate = it },

        isExam = addVm.isExam,
        onExamToggle = { addVm.isExam = it },

        examRoom = addVm.examRoom,
        onExamRoomChange = { addVm.examRoom = it },

        examHour = addVm.examHour,
        onExamHourChange = { addVm.examHour = it },

        gradePercent = addVm.gradePercent,
        onGradePercentChange = { addVm.gradePercent = it },

        enabled = addVm.isValid(),
        submitText = stringResource(R.string.ui_add),

        onSubmit = {
            val task = if (addVm.isExam) {
                Exam(
                    id = 0, // room deberia de hacerlo por nosotros
                    subjectId = addVm.subjectId!!, // nunca sera null por isValid check
                    title = addVm.title,
                    dueDate = addVm.dueDate!!, // nunca sera null por isValid check
                    isCompleted = false,
                    examRoom = addVm.examRoom,
                    examHour = addVm.examHour!!, // nunca sera null por isValid check
                    gradePercent = addVm.gradePercent.toFloat()
                )
            } else {
                Task(
                    id = 0, // room deberia de hacerlo por nosotros
                    subjectId = addVm.subjectId,
                    title = addVm.title,
                    dueDate = addVm.dueDate!!, // nunca sera null por isValid check
                    isCompleted = false
                )
            }

            vm.addTask(task)
            navController.navigate(Routes.TASKS)
        }
    )
}