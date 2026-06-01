package es.upsa.papps.trabajofinal.ui.screens.subject

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
import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel
import es.upsa.papps.trabajofinal.viewmodel.SubjectFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.ui_edit_subject))
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
                        enabled = editVm.isValid(),
                    ) {
                        Text(stringResource(R.string.ui_save))
                    }
                },
            )
        },
    ) { padding ->
        AbstractSubjectForm(
            name = editVm.name,
            onNameChange = { editVm.name = it },
            teacher = editVm.teacher,
            onTeacherChange = { editVm.teacher = it },
            credits = editVm.credits,
            onCreditsChange = { editVm.credits = it },
            modifier = Modifier.padding(padding)
        )
    }
}