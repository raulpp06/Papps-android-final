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
        MainUiState(
            subjects = subjects,
            tasks = tasks,
            exams = exams,
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

    fun updateTaskBase(original: TaskBase, updated: TaskBase) {
        viewModelScope.launch {

            when (original) {

                is Task -> {
                    when (updated) {

                        is Task -> {
                            taskRepo.update(updated)
                        }

                        is Exam -> {
                            taskRepo.deleteById(original.id)
                            examRepo.insert(updated.copy(id = 0))
                        }
                    }
                }

                is Exam -> {
                    when (updated) {

                        is Exam -> {
                            examRepo.update(updated)
                        }

                        is Task -> {
                            examRepo.deleteById(original.id)
                            taskRepo.insert(updated.copy(id = 0))
                        }
                    }
                }
            }
        }
    }

    fun toggleTask(id: Int) {
        viewModelScope.launch {
            val task = uiState.value.tasks.find { it.id == id } ?: return@launch
            taskRepo.setCompleted(id, !task.isCompleted)
        }
    }

    fun toggleExam(id: Int) {
        viewModelScope.launch {
            val task = uiState.value.exams.find { it.id == id } ?: return@launch
            examRepo.setCompleted(id, !task.isCompleted)
        }
    }

    fun deleteTask(id: Int) {
        viewModelScope.launch {
            val task = uiState.value.tasks.find { it.id == id } ?: return@launch
            taskRepo.deleteById(id)
        }
    }

    fun deleteExam(id: Int) {
        viewModelScope.launch {
            val exam = uiState.value.exams.find { it.id == id } ?: return@launch
            examRepo.deleteById(id)
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