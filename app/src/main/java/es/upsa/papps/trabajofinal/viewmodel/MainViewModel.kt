package es.upsa.papps.trabajofinal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.model.data.Task
import es.upsa.papps.trabajofinal.model.data.TaskBase
import es.upsa.papps.trabajofinal.model.data.TaskFilters
import es.upsa.papps.trabajofinal.repository.AppTheme
import es.upsa.papps.trabajofinal.repository.ThemePreferenceRepository
import es.upsa.papps.trabajofinal.state.MainUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState =
        MutableStateFlow(
            MainUiState(
                subjects = listOf(),
                tasks = listOf(),
                activeTaskFilters = TaskFilters(),
            ),
        )

    private val themeRepo = ThemePreferenceRepository(application)

    val appTheme: StateFlow<AppTheme> = themeRepo.theme
        .stateIn(viewModelScope, SharingStarted.Eagerly, AppTheme.SYSTEM)

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

    fun deleteTask(
        id: Int,
    ) {
        val updated =
            _uiState.value.tasks.filter { it.id != id }

        _uiState.value = _uiState.value.copy(tasks = updated)
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

    fun deleteSubject(
        id: Int,
    ) {
        val updated =
            _uiState.value.subjects.filter { it.id != id }

        _uiState.value = _uiState.value.copy(subjects = updated)
    }

    fun updateFilters(newFilters: TaskFilters) {
        _uiState.value = _uiState.value.copy(activeTaskFilters = newFilters)
    }

    fun clearFilters(newFilters: TaskFilters) {
        _uiState.value = _uiState.value.copy(activeTaskFilters = TaskFilters())
    }

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            themeRepo.setTheme(theme)
        }
    }
}
