package es.upsa.papps.trabajofinal.model.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import es.upsa.papps.trabajofinal.model.data.Task
import java.time.LocalDate

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.SET_NULL,
        )
    ],
    indices = [Index("subjectId")]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val subjectId: Int?,
    val title: String,
    val dueDate: LocalDate,
    val isCompleted: Boolean,
) {
    fun toDomain(): Task = Task(
        id = id,
        subjectId = subjectId,
        title = title,
        dueDate = dueDate,
        isCompleted = isCompleted,
    )

    companion object {
        fun fromDomain(task: Task): TaskEntity = TaskEntity(
            id = task.id,
            subjectId = task.subjectId,
            title = task.title,
            dueDate = task.dueDate,
            isCompleted = task.isCompleted,
        )
    }
}