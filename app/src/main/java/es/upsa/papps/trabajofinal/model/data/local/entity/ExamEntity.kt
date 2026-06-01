package es.upsa.papps.trabajofinal.model.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import es.upsa.papps.trabajofinal.model.data.Exam
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "exams",
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index("subjectId")]
)
data class ExamEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val subjectId: Int,
    val title: String,
    val dueDate: LocalDate,
    val isCompleted: Boolean,
    val examRoom: String,
    val examHour: LocalTime,
    val gradePercent: Float,
) {
    fun toDomain(): Exam = Exam(
        id = id,
        subjectId = subjectId,
        title = title,
        dueDate = dueDate,
        isCompleted = isCompleted,
        examRoom = examRoom,
        examHour = examHour,
        gradePercent = gradePercent,
    )

    companion object {
        fun fromDomain(exam: Exam): ExamEntity = ExamEntity(
            id = exam.id,
            subjectId = exam.subjectId,
            title = exam.title,
            dueDate = exam.dueDate,
            isCompleted = exam.isCompleted,
            examRoom = exam.examRoom,
            examHour = exam.examHour,
            gradePercent = exam.gradePercent,
        )
    }
}