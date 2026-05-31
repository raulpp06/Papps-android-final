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
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel

@Composable
fun SubjectDetailScreen(
    id: Int,
    vm: MainViewModel
) {
    val state by vm.uiState.collectAsState()

    val subject = state.subjects.find { subject -> subject.id == id }

    Column(modifier = Modifier.padding(16.dp)) {

        if (subject == null) {
            Text("Asignatura no encontrada")
            return@Column
        }

        Text(
            text = "Detalle de asignatura",
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Nombre: ${subject.name}")

        Text("Creditos: ${subject.credits}")
    }
}