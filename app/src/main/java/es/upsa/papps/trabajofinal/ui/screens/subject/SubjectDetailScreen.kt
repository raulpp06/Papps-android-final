package es.upsa.papps.trabajofinal.ui.screens.subject

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.navigation.Routes
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectDetailScreen(
    navController: NavController,
    id: Int,
    vm: MainViewModel,
) {
    val state by vm.uiState.collectAsState()

    val subject = state.subjects.find { it.id == id }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val texto = subject?.name ?: "Asignatura no encontrada"
                    Text(texto)
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate("${Routes.SUBJECT_EDIT}/${subject?.id}")
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar asignatura",
                        )
                    }
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                            vm.deleteSubject(id)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Borrar asignatura",
                        )
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
        ) {
            if (subject == null) {
                Text("Asignatura no encontrada")
                return@Column
            }

            val subjectTasks = state.tasks.filter {
                it.subjectId == subject.id
            }

            Text("Créditos: ${subject.credits}")

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Tareas y exámenes",
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (subjectTasks.isEmpty()) {
                Text("No hay tareas asociadas")
            } else {
                subjectTasks.forEach { task ->
                    val icon = if (task is Exam) {
                        Icons.Default.Warning
                    } else {
                        Icons.Default.Star
                    }

                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.navigate("${Routes.TASK_DETAIL}/${task.id}")
                            },
                    ) {
                        Icon(icon, null)
                        Text(
                            text = "${task.title} (${task.dueDate})",
                        )
                    }
                }
            }
        }
    }
}
