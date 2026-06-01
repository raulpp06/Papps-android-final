package es.upsa.papps.trabajofinal.state

import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.model.data.Task
import es.upsa.papps.trabajofinal.model.data.TaskBase
import es.upsa.papps.trabajofinal.model.data.TaskFilters
import es.upsa.papps.trabajofinal.model.data.filter.TaskStatusFilter
import es.upsa.papps.trabajofinal.model.data.filter.TaskSubjectFilter
import es.upsa.papps.trabajofinal.model.data.filter.TaskTypeFilter

data class MainUiState(
    val subjects: List<Subject> = emptyList(),
    val tasks: List<Task> = emptyList(),
    val exams: List<Exam> = emptyList(),
    val activeTaskFilters: TaskFilters = TaskFilters(),
    val isLoading: Boolean = true,
) {

    val filteredTasks: List<Task>
        get() = filterList(
            tasks,
            activeTaskFilters
        )

    val filteredExams: List<Exam>
        get() = filterList(
            exams,
            activeTaskFilters
        )

    companion object {
        fun <T : TaskBase> filterList(list: List<T>, filters: TaskFilters) =
            list.filter { task ->

                val statusMatch = when (filters.status) {
                    TaskStatusFilter.ALL -> true
                    TaskStatusFilter.PENDING -> !task.isCompleted
                    TaskStatusFilter.COMPLETED -> task.isCompleted
                }

                val subjectMatch = when (val s = filters.subject) {
                    TaskSubjectFilter.All -> true
                    TaskSubjectFilter.NoSubject -> task.subjectId == null
                    is TaskSubjectFilter.Subject -> task.subjectId == s.subjectId
                }

                val typeMatch = when (filters.taskType) {
                    TaskTypeFilter.ALL -> true
                    TaskTypeFilter.EXAM -> task is Exam
                    TaskTypeFilter.TASK -> task is Task
                }

                val dateMatch =
                    (filters.fromDate == null || !task.dueDate.isBefore(filters.fromDate)) &&
                            (filters.toDate == null || !task.dueDate.isAfter(filters.toDate))

                statusMatch && subjectMatch && typeMatch && dateMatch
            }
    }
}