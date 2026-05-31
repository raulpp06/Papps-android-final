package es.upsa.papps.trabajofinal.state

import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.model.data.Task
import es.upsa.papps.trabajofinal.model.data.TaskBase
import es.upsa.papps.trabajofinal.model.data.TaskFilter
import es.upsa.papps.trabajofinal.model.data.TaskFilters

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

            val statusMatch = when (f.status) {
                TaskFilter.ALL -> true
                TaskFilter.PENDING -> !task.isCompleted
                TaskFilter.COMPLETED -> task.isCompleted
                TaskFilter.EXAMS -> task is Exam
                TaskFilter.TASKS -> task is Task
            }

            val subjectMatch =
                f.subjectId == null || task.subjectId == f.subjectId

            val typeMatch =
                !f.onlyExams || task is Exam

            val dateMatch =
                (f.fromDate == null || task.dueDate >= f.fromDate) &&
                        (f.toDate == null || task.dueDate <= f.toDate)

            statusMatch && subjectMatch && typeMatch && dateMatch
        }
}
