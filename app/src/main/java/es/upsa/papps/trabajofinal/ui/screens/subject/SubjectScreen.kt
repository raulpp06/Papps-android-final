package es.upsa.papps.trabajofinal.ui.screens.subject

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
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
import es.upsa.papps.trabajofinal.navigation.Routes
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectsScreen(
    navController: NavController,
    vm: MainViewModel,
) {
    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asignaturas") },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Routes.SUBJECT_ADD)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Añadir asignatura",
                        )
                    }
                },
            )
        },
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
        ) {
            items(state.subjects) { subject ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("${Routes.SUBJECT_DETAIL}/${subject.id}")
                        },
                ) {
                    Column(
                        Modifier.padding(16.dp),
                    ) {
                        Text(
                            text = subject.name,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text("Créditos: ${subject.credits}")
                    }
                }
            }
        }
    }
}
