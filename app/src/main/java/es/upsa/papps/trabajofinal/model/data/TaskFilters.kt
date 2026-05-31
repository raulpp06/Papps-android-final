package es.upsa.papps.trabajofinal.model.data

import es.upsa.papps.trabajofinal.model.data.filter.TaskStatusFilter
import es.upsa.papps.trabajofinal.model.data.filter.TaskSubjectFilter
import es.upsa.papps.trabajofinal.model.data.filter.TaskTypeFilter
import java.time.LocalDate

data class TaskFilters(
    val status: TaskStatusFilter = TaskStatusFilter.ALL,
    val subject: TaskSubjectFilter = TaskSubjectFilter.All,
    val taskType: TaskTypeFilter = TaskTypeFilter.ALL,
    val fromDate: LocalDate? = null,
    val toDate: LocalDate? = null,
)