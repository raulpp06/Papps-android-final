package es.upsa.papps.trabajofinal.model.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import es.upsa.papps.trabajofinal.model.data.Subject

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val teacher: String,
    val credits: Int,
) {
    fun toDomain(): Subject = Subject(
        id = id,
        name = name,
        teacher = teacher,
        credits = credits,
    )

    companion object {
        fun fromDomain(subject: Subject): SubjectEntity = SubjectEntity(
            id = subject.id,
            name = subject.name,
            teacher = subject.teacher,
            credits = subject.credits,
        )
    }
}