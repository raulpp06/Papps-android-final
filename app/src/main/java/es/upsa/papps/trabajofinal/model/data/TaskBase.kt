package es.upsa.papps.trabajofinal.model.data

sealed class TaskBase(
    open val id: Int,
    open val subjectId: Int,
    open val title: String,
    open val dueDate: String,
    open val isCompleted: Boolean
)