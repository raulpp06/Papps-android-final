package es.upsa.papps.trabajofinal.model.repository

import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.model.data.TaskFilters
import es.upsa.papps.trabajofinal.model.data.filter.TaskStatusFilter
import es.upsa.papps.trabajofinal.model.data.filter.TaskSubjectFilter
import es.upsa.papps.trabajofinal.model.data.local.dao.ExamDao
import es.upsa.papps.trabajofinal.model.data.local.entity.ExamEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExamRepository(private val dao: ExamDao) {

    suspend fun insert(exam: Exam): Long =
        dao.insert(ExamEntity.fromDomain(exam))

    suspend fun insertAll(exams: List<Exam>) =
        dao.insertAll(exams.map { ExamEntity.fromDomain(it) })

    fun getAllFlow(): Flow<List<Exam>> =
        dao.getAllFlow().map { list -> list.map { it.toDomain() } }

    suspend fun getAll(): List<Exam> =
        dao.getAll().map { it.toDomain() }

    suspend fun getById(id: Int): Exam? =
        dao.getById(id)?.toDomain()

    fun getFilteredFlow(filters: TaskFilters): Flow<List<Exam>> {
        val subjectId: Int? = when (val s = filters.subject) {
            is TaskSubjectFilter.All -> null
            is TaskSubjectFilter.NoSubject -> return dao.getAllFlow()
                .map { emptyList() } // examenes siempre tienen una asignatura
            is TaskSubjectFilter.Subject -> s.subjectId
        }

        val isCompleted: Boolean? = when (filters.status) {
            TaskStatusFilter.ALL -> null
            TaskStatusFilter.PENDING -> false
            TaskStatusFilter.COMPLETED -> true
        }

        return dao.getFilteredFlow(
            subjectId = subjectId,
            isCompleted = isCompleted,
            fromDate = filters.fromDate,
            toDate = filters.toDate,
        ).map { list -> list.map { it.toDomain() } }
    }

    suspend fun update(exam: Exam) =
        dao.update(ExamEntity.fromDomain(exam))

    suspend fun setCompleted(id: Int, completed: Boolean) =
        dao.setCompleted(id, completed)

    suspend fun delete(exam: Exam) =
        dao.delete(ExamEntity.fromDomain(exam))

    suspend fun deleteById(id: Int) =
        dao.deleteById(id)

    suspend fun deleteAll() =
        dao.deleteAll()
}