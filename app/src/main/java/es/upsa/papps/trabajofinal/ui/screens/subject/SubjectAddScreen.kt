package es.upsa.papps.trabajofinal.ui.screens.subject

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.upsa.papps.trabajofinal.R
import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.navigation.Routes
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel
import es.upsa.papps.trabajofinal.viewmodel.SubjectFormViewModel

@Composable
fun SubjectAddScreen(
    navController: NavController,
    vm: MainViewModel,
    addVm: SubjectFormViewModel = viewModel(),
) {
    AbstractSubjectForm(
        pageTitle = stringResource(R.string.ui_new_subject),
        name = addVm.name,
        onNameChange = { addVm.name = it },
        teacher = addVm.teacher,
        onTeacherChange = { addVm.teacher = it },
        credits = addVm.credits,
        onCreditsChange = { addVm.credits = it },
        enabled = addVm.isValid(),
        submitText = stringResource(R.string.ui_add),
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
