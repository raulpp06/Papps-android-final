package es.upsa.papps.trabajofinal.ui.screens.edit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.ui.screens.util.SubjectForm
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

    println("EDIT + subjects size = ${state.subjects.size}")
    println("EDIT + looking for id = $id")
    println("EDIT + subject = $subject")

    LaunchedEffect(subject?.id) {
        if (subject != null) {
            editVm.loadSubject(subject)
            println("EDIT + name = ${editVm.name}")
            println("EDIT + teacher = ${editVm.teacher}")
            println("EDIT + credits = ${editVm.credits}")
        }
    }

    if (subject == null) {
        Text("Asignatura no encontrada")
        return
    }

    SubjectForm(
        pageTitle = "Editar ${subject.name}",
        name = editVm.name,
        onNameChange = { editVm.name = it },
        teacher = editVm.teacher,
        onTeacherChange = { editVm.teacher = it },
        credits = editVm.credits,
        onCreditsChange = { editVm.credits = it },
        enabled = editVm.isValid(),
        submitText = "Guardar",
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
