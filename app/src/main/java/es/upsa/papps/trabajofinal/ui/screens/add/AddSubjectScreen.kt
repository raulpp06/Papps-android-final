package es.upsa.papps.trabajofinal.ui.screens.add

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.navigation.Routes
import es.upsa.papps.trabajofinal.ui.screens.util.SubjectForm
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel
import es.upsa.papps.trabajofinal.viewmodel.SubjectFormViewModel

@Composable
fun AddSubjectScreen(
    navController: NavController,
    vm: MainViewModel,
    addVm: SubjectFormViewModel = viewModel(),
) {
    SubjectForm(
        pageTitle = "Nueva asignatura",
        name = addVm.name,
        onNameChange = { addVm.name = it },
        teacher = addVm.teacher,
        onTeacherChange = { addVm.teacher = it },
        credits = addVm.credits,
        onCreditsChange = { addVm.credits = it },
        enabled = addVm.isValid(),
        submitText = "Añadir",
        onSubmit = {
            vm.addSubject(
                Subject(
                    id = vm.uiState.value.subjects.size + 1,
                    name = addVm.name,
                    teacher = addVm.teacher,
                    credits = addVm.credits.toInt(),
                ),
            )

            navController.navigate(Routes.SUBJECTS)
        },
    )
}
