package es.upsa.papps.trabajofinal.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.model.data.Task
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel

@Composable
fun TaskDetailScreen(
    id: Int,
    vm: MainViewModel
) {
    val state by vm.uiState.collectAsState()

    val task = state.tasks.find { it.id == id }

    Column(modifier = Modifier.padding(16.dp)) {

        if (task == null) {
            Text("Tarea no encontrada")
            return@Column
        }

        Text(
            text = when (task) {
                is Exam -> "Detalle de Examen"
                is Task -> "Detalle de Tarea"
            },
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Nombre: ${task.title}")
        Text("Fecha fin: ${task.dueDate}")

        Spacer(modifier = Modifier.height(16.dp))

        when (task) {

            is Exam -> {
                Text("Aula: ${task.examRoom}")
                Text("Hora: ${task.examHour}")
                Text("Peso: ${task.gradePercent}")
            }

            is Task -> {
                // NADA
                Text("Tarea normal (sin datos extra)") // TEST TODO: quitar para envio
            }
        }
    }
}