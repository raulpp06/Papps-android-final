package es.upsa.papps.trabajofinal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.model.data.Task
import es.upsa.papps.trabajofinal.model.data.TaskBase
import es.upsa.papps.trabajofinal.model.data.TaskFilters
import es.upsa.papps.trabajofinal.model.data.local.AppDatabase
import es.upsa.papps.trabajofinal.model.repository.AppTheme
import es.upsa.papps.trabajofinal.model.repository.ExamRepository
import es.upsa.papps.trabajofinal.model.repository.SubjectRepository
import es.upsa.papps.trabajofinal.model.repository.TaskRepository
import es.upsa.papps.trabajofinal.model.repository.ThemePreferenceRepository
import es.upsa.papps.trabajofinal.state.MainUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val taskRepo = TaskRepository(db.taskDao())
    private val examRepo = ExamRepository(db.examDao())
    private val subjectRepo = SubjectRepository(db.subjectDao())

    private val themeRepo = ThemePreferenceRepository(application)

    val appTheme: StateFlow<AppTheme> = themeRepo.theme
        .stateIn(viewModelScope, SharingStarted.Eagerly, AppTheme.SYSTEM)

    private val _filters = MutableStateFlow(TaskFilters())

    val uiState: StateFlow<MainUiState> = combine(
        subjectRepo.getAllFlow(),
        taskRepo.getAllFlow(),
        examRepo.getAllFlow(),
        _filters,
    ) { subjects, tasks, exams, filters ->
        val allTasks: List<TaskBase> = (tasks + exams).sortedBy { it.dueDate }

        MainUiState(
            subjects = subjects,
            tasks = allTasks,
            activeTaskFilters = filters,
            isLoading = false,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainUiState(isLoading = true),
    )

    fun addTask(task: TaskBase) {
        viewModelScope.launch {
            when (task) {
                is Task -> taskRepo.insert(task)
                is Exam -> examRepo.insert(task)
            }
        }
    }

    fun modifyTask(id: Int, task: TaskBase) {
        viewModelScope.launch {
            when (task) {
                is Task -> taskRepo.update(task)
                is Exam -> examRepo.update(task)
            }
        }
    }

    fun toggleTask(id: Int) {
        viewModelScope.launch {
            // Check tasks first, then exams
            val task = uiState.value.tasks.find { it.id == id } ?: return@launch
            when (task) {
                is Task -> taskRepo.setCompleted(id, !task.isCompleted)
                is Exam -> examRepo.setCompleted(id, !task.isCompleted)
            }
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            // We need to know the type; look it up in the current state
            val task = uiState.value.tasks.find { it.id == id } ?: return@launch
            when (task) {
                is Task -> taskRepo.deleteById(id)
                is Exam -> examRepo.deleteById(id)
            }
        }
    }

    fun addSubject(subject: Subject) {
        viewModelScope.launch {
            subjectRepo.insert(subject)
        }
    }

    fun modifySubject(id: Int, subject: Subject) {
        viewModelScope.launch {
            subjectRepo.update(subject)
        }
    }

    fun deleteSubject(id: Int) {
        viewModelScope.launch {
            subjectRepo.deleteById(id)
        }
    }

    fun updateFilters(newFilters: TaskFilters) {
        _filters.value = newFilters
    }

    fun clearFilters() {
        _filters.value = TaskFilters()
    }

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            themeRepo.setTheme(theme)
        }
    }
}