package es.upsa.papps.trabajofinal.model.data

data class TaskFilters(
    val status: TaskFilter = TaskFilter.ALL,
    val subjectId: Int? = null,
    val onlyExams: Boolean = false,
    val fromDate: String? = null,
    val toDate: String? = null,
)