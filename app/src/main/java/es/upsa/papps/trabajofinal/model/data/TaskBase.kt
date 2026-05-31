package es.upsa.papps.trabajofinal.model.data

import java.time.LocalDate

sealed class TaskBase(
    open val id: Int,
    open val subjectId: Int?,
    open val title: String,
    open val dueDate: LocalDate,
    open val isCompleted: Boolean,
)
