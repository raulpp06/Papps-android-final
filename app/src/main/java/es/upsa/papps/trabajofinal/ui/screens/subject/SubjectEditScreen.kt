package es.upsa.papps.trabajofinal.ui.screens.subject

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.upsa.papps.trabajofinal.R
import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel
import es.upsa.papps.trabajofinal.viewmodel.SubjectFormViewModel

@Composable
fun SubjectEditScreen(
    navController: NavController,
    id: Int,
    vm: MainViewModel,
    editVm: SubjectFormViewModel = viewModel(),
) {
    val state by vm.uiState.collectAsState()
    val subject = state.subjects.find { it.id == id }

    LaunchedEffect(subject?.id) {
        if (subject != null) {
            editVm.loadSubject(subject)
        }
    }

    if (subject == null) {
        Text(stringResource(R.string.ui_subject_not_found))
        return
    }

    AbstractSubjectForm(
        pageTitle = stringResource(R.string.ui_edit_subject_title, subject.name),
        name = editVm.name,
        onNameChange = { editVm.name = it },
        teacher = editVm.teacher,
        onTeacherChange = { editVm.teacher = it },
        credits = editVm.credits,
        onCreditsChange = { editVm.credits = it },
        enabled = editVm.isValid(),
        submitText = stringResource(R.string.ui_save),
        onSubmit = {
            vm.modifySubject(
                id,
                Subject(
                    id = id,
                    name = editVm.name,
                    teacher = editVm.teacher,
                    credits = editVm.credits.toInt(),
                ),
            )
            navController.popBackStack()
        },
    )
}