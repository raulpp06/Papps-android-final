package es.upsa.papps.trabajofinal.ui.screens.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import es.upsa.papps.trabajofinal.R
import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.model.data.Task
import es.upsa.papps.trabajofinal.navigation.Routes
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    navController: NavController,
    id: Int,
    vm: MainViewModel,
    type: String,
) {
    val state by vm.uiState.collectAsState()
    val task = when (type) {
        Routes.Subroutes.TASK -> state.tasks.find { it.id == id }
        Routes.Subroutes.EXAM -> state.exams.find { it.id == id }
        else -> return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(task?.title ?: stringResource(R.string.ui_task_not_found))
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
                    IconButton(
                        onClick = {
                            navController.navigate("${Routes.TASK_EDIT}/${type}/${task?.id}")
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.ui_edit_task),
                        )
                    }
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                            when (task) {
                                is Exam -> vm.deleteExam(id)
                                is Task -> vm.deleteTask(id)
                                else -> {error("unreachable")}
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.ui_delete_task),
                        )
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(padding),
        ) {
            if (task == null) {
                Text(stringResource(R.string.ui_task_not_found))
                return@Column
            }

            Text(
                text = when (task) {
                    is Exam -> stringResource(R.string.ui_exam_detail)
                    is Task -> stringResource(R.string.ui_task_detail)
                },
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(stringResource(R.string.ui_name_label, task.title))
            Text(stringResource(R.string.ui_due_date_label, task.dueDate))

            Spacer(modifier = Modifier.height(16.dp))

            when (task) {
                is Exam -> {
                    Text(stringResource(R.string.ui_room_label, task.examRoom))
                    Text(stringResource(R.string.ui_hour_label, task.examHour))
                    Text(stringResource(R.string.ui_weight_label, task.gradePercent))
                }

                is Task -> {
                    Text(stringResource(R.string.ui_task_normal))
                }
            }
        }
    }
}
