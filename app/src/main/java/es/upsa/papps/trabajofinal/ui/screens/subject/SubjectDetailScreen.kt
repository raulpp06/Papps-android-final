package es.upsa.papps.trabajofinal.ui.screens.subject

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import es.upsa.papps.trabajofinal.R
import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.navigation.Routes
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel

@Suppress("AssignedValueIsNeverRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectDetailScreen(
    navController: NavController,
    id: Int,
    vm: MainViewModel,
) {
    val state by vm.uiState.collectAsState()
    val subject = state.subjects.find { it.id == id }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(subject?.name ?: stringResource(R.string.ui_subject_not_found))
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
                            navController.navigate("${Routes.SUBJECT_EDIT}/${subject?.id}")
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.ui_edit_subject),
                        )
                    }
                    IconButton(
                        onClick = {
                            showDeleteDialog = true
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.ui_delete_subject),
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
                Text(stringResource(R.string.ui_subject_not_found))
                return@Column
            }

            val subjectTasks = state.tasks.filter { it.subjectId == subject.id }
            val subjectExams = state.exams.filter { it.subjectId == subject.id }
            val subjectStuff = subjectExams + subjectTasks

            Text(stringResource(R.string.ui_credits_label, subject.credits))

            Spacer(modifier = Modifier.height(24.dp))

            Text(stringResource(R.string.ui_profesor_label, subject.teacher))

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.ui_tasks_and_exams),
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (subjectStuff.isEmpty()) {
                Text(stringResource(R.string.ui_no_tasks))
            } else {
                subjectStuff.forEach { task ->
                    val icon = if (task is Exam) Icons.Default.Warning else Icons.Default.Star
                    val type = if (task is Exam) Routes.Subroutes.EXAM else Routes.Subroutes.TASK

                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.navigate("${Routes.TASK_DETAIL}/$type/${task.id}")
                            },
                    ) {
                        Icon(icon, null)
                        Text(stringResource(R.string.ui_task_due_date, task.title, task.dueDate))
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(stringResource(R.string.ui_delete_subject))
            },
            text = {
                Text(stringResource(R.string.ui_delete_subject_alert))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        navController.popBackStack()
                        vm.deleteSubject(id)
                    }
                ) {
                    Text(stringResource(R.string.ui_delete))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text(stringResource(R.string.ui_cancel))
                }
            }
        )
    }
}