package es.upsa.papps.trabajofinal.ui.screens.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.model.data.Task
import es.upsa.papps.trabajofinal.navigation.Routes
import es.upsa.papps.trabajofinal.ui.screens.util.TaskForm
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel
import es.upsa.papps.trabajofinal.viewmodel.TaskFormViewModel

@Composable
fun AddTaskScreen(
    navController: NavController,
    vm: MainViewModel,
    addVm: TaskFormViewModel = viewModel()
) {
    val subjects = vm.uiState.collectAsState().value.subjects

    TaskForm(
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
        submitText = "Añadir",

        onSubmit = {

            val subjectId = addVm.subjectId ?: return@TaskForm

            val task = if (addVm.isExam) {
                Exam(
                    id = vm.uiState.value.tasks.size + 1,
                    subjectId = subjectId,
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
                    subjectId = subjectId,
                    title = addVm.title,
                    dueDate = addVm.dueDate,
                    isCompleted = false
                )
            }

            vm.addTask(task)
            navController.navigate(Routes.TASKS)
        }
    )
}