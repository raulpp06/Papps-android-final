package es.upsa.papps.trabajofinal.model.data

import java.time.LocalDate

data class Task(
    override val id: Int,
    override val subjectId: Int?,
    override val title: String,
    override val dueDate: LocalDate,
    override val isCompleted: Boolean,
) : TaskBase(id, subjectId, title, dueDate, isCompleted)
