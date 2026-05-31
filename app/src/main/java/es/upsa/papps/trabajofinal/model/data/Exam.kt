package es.upsa.papps.trabajofinal.model.data

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Exam(
    override val id: Int,
    override val subjectId: Int,
    override val title: String,
    override val dueDate: LocalDate,
    override val isCompleted: Boolean,
    val examRoom: String,
    val examHour: LocalTime,
    val gradePercent: Float // de 0 a 1
) : TaskBase(id, subjectId, title, dueDate, isCompleted) {
    fun isFinished(): Boolean {
        val examDateTime = LocalDateTime.of(
            dueDate,
            examHour
        )

        return LocalDateTime.now() > examDateTime
    }
}
