package es.upsa.papps.trabajofinal.model.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

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
    @RequiresApi(Build.VERSION_CODES.O)
    fun isFinished(): Boolean {
        val examDateTime = LocalDateTime.of(
            LocalDate.parse(dueDate),
            LocalTime.parse(examHour)
        )

        return LocalDateTime.now() > examDateTime
    }
}
