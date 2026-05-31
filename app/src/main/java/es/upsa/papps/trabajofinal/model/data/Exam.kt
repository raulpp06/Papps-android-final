package es.upsa.papps.trabajofinal.model.data

data class Exam(
    override val id: Int,
    override val subjectId: Int,
    override val title: String,
    override val dueDate: String,
    override val isCompleted: Boolean,
    val examRoom: String,
    val examHour: String,
    val gradePercent: Float // de 0 a 1
) : TaskBase(id, subjectId, title, dueDate, isCompleted) {
}
