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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.upsa.papps.trabajofinal.R
import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.navigation.Routes
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel
import es.upsa.papps.trabajofinal.viewmodel.SubjectFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectAddScreen(
    navController: NavController,
    vm: MainViewModel,
    addVm: SubjectFormViewModel = viewModel(),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.ui_add_subject))
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
                            vm.addSubject(
                                Subject(
                                    id = 0, // room deberia de hacerlo por nosotros
                                    name = addVm.name,
                                    teacher = addVm.teacher,
                                    credits = addVm.credits.toInt(),
                                ),
                            )

                            navController.navigate(Routes.SUBJECTS)
                        },
                        enabled = addVm.isValid(),
                    ) {
                        Text(stringResource(R.string.ui_add))
                    }
                },
            )
        },
    ) { padding ->
        AbstractSubjectForm(
            name = addVm.name,
            onNameChange = { addVm.name = it },
            nameIsError = addVm.name.isBlank(),
            teacher = addVm.teacher,
            onTeacherChange = { addVm.teacher = it },
            teacherIsError = addVm.teacher.isBlank(),
            credits = addVm.credits,
            onCreditsChange = { addVm.credits = it },
            modifier = Modifier.padding(padding)
        )
    }
}
