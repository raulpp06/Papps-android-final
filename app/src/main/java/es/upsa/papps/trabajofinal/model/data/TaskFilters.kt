package es.upsa.papps.trabajofinal.model.data

import java.time.LocalDate

data class TaskFilters(
    val status: TaskFilter = TaskFilter.ALL,
    val subjectId: Int? = null,
    val onlyExams: Boolean = false,
    val fromDate: LocalDate? = null,
    val toDate: LocalDate? = null,
)