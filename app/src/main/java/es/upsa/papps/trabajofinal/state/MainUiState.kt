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
    val subjects: List<Subject>,
    val tasks: List<TaskBase>,
    val activeTaskFilters: TaskFilters,
) {
    // Si añado propiedades no olvidar
    fun isEmpty(): Boolean = subjects.isEmpty() && tasks.isEmpty()

    val filteredTasks: List<TaskBase>
        get() = tasks.filter { task ->

            val f = activeTaskFilters

            // ---------------- STATUS ----------------
            val statusMatch = when (f.status) {
                TaskStatusFilter.ALL -> true
                TaskStatusFilter.PENDING -> !task.isCompleted
                TaskStatusFilter.COMPLETED -> task.isCompleted
            }

            // ---------------- SUBJECT ----------------
            val subjectMatch = when (val s = f.subject) {
                TaskSubjectFilter.All -> true

                TaskSubjectFilter.NoSubject ->
                    task.subjectId == null

                is TaskSubjectFilter.Subject ->
                    task.subjectId == s.subjectId
            }

            // ---------------- TYPE ----------------
            val typeMatch = when (f.taskType) {
                TaskTypeFilter.ALL -> true
                TaskTypeFilter.EXAM -> task is Exam
                TaskTypeFilter.TASK -> task is Task
            }

            // ---------------- DATE ----------------
            val dateMatch =
                (f.fromDate == null || !task.dueDate.isBefore(f.fromDate)) &&
                        (f.toDate == null || !task.dueDate.isAfter(f.toDate))

            statusMatch && subjectMatch && typeMatch && dateMatch
        }
}
