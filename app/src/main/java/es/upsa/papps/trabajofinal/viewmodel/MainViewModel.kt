package es.upsa.papps.trabajofinal.viewmodel

import androidx.lifecycle.ViewModel
import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.model.data.Task
import es.upsa.papps.trabajofinal.model.data.TaskBase
import es.upsa.papps.trabajofinal.model.data.TaskFilters
import es.upsa.papps.trabajofinal.state.MainUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            MainUiState(
                subjects = listOf(),
                tasks = listOf(),
                activeTaskFilters = TaskFilters(),
            ),
        )

    val uiState: StateFlow<MainUiState> = _uiState

    fun addTask(task: TaskBase) {
        val updated = _uiState.value.tasks + task

        _uiState.value = _uiState.value.copy(tasks = updated)
    }

    fun modifyTask(
        id: Int,
        task: TaskBase,
    ) {
        val updated =
            _uiState.value.tasks.map {
                if (it.id == id) task else it
            }

        _uiState.value = _uiState.value.copy(tasks = updated)
    }

    fun toggleTask(id: Int) {
        val task = _uiState.value.tasks.find { it.id == id } ?: return

        val updated = if (task is Task) {
            task.copy(isCompleted = !task.isCompleted)
        } else {
            return
        }

        modifyTask(id, updated)
    }

    fun addSubject(subject: Subject) {
        val updated = _uiState.value.subjects + subject

        _uiState.value = _uiState.value.copy(subjects = updated)
    }

    fun modifySubject(
        id: Int,
        subject: Subject,
    ) {
        val updated =
            _uiState.value.subjects.map {
                if (it.id == id) subject else it
            }

        _uiState.value = _uiState.value.copy(subjects = updated)
    }

    fun updateFilters(newFilters: TaskFilters) {
        _uiState.value = _uiState.value.copy(activeTaskFilters = newFilters)
    }
}
