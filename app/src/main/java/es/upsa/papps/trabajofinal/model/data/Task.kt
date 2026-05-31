package es.upsa.papps.trabajofinal.model.data

data class Task(
    override val id: Int,
    override val subjectId: Int?,
    override val title: String,
    override val dueDate: String,
    override val isCompleted: Boolean,
) : TaskBase(id, subjectId, title, dueDate, isCompleted)
