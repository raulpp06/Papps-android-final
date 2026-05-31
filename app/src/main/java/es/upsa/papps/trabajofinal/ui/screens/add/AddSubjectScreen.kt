package es.upsa.papps.trabajofinal.ui.screens.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.navigation.Routes
import es.upsa.papps.trabajofinal.viewmodel.AddSubjectViewModel
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel

@Composable
fun AddSubjectScreen(
    navController: NavController,
    vm: MainViewModel,
    addVm: AddSubjectViewModel = viewModel(),
) {
    Column(Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = addVm.name,
            onValueChange = { addVm.name = it },
            label = { Text("Nombre") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = addVm.teacher,
            onValueChange = { addVm.teacher = it },
            label = { Text("Profesor") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        val showError = addVm.credits.isNotBlank() && !addVm.isCreditsValid()
        OutlinedTextField(
            value = addVm.credits,
            onValueChange = { addVm.credits = it },
            isError = showError,
            supportingText = {
                if (showError) {
                    Text("Introduce un número positivo")
                }
            },
            label = { Text("Créditos") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val subject = Subject(
                    id = vm.uiState.value.subjects.size + 1,
                    name = addVm.name,
                    teacher = addVm.teacher,
                    credits = addVm.credits.toInt(),
                )

                vm.addSubject(subject)
                navController.navigate(Routes.SUBJECTS)
            },
            enabled = addVm.isValid()
        ) {
            Text("Añadir")
        }
    }
}