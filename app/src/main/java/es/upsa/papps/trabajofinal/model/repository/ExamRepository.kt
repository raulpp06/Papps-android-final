package es.upsa.papps.trabajofinal.model.repository

import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.model.data.local.dao.ExamDao
import es.upsa.papps.trabajofinal.model.data.local.entity.ExamEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExamRepository(private val dao: ExamDao) {

    suspend fun insert(exam: Exam): Long =
        dao.insert(ExamEntity.fromDomain(exam))

    fun getAllFlow(): Flow<List<Exam>> =
        dao.getAllFlow().map { list -> list.map { it.toDomain() } }

    suspend fun update(exam: Exam) =
        dao.update(ExamEntity.fromDomain(exam))

    suspend fun setCompleted(id: Int, completed: Boolean) =
        dao.setCompleted(id, completed)

    suspend fun delete(exam: Exam) =
        dao.delete(ExamEntity.fromDomain(exam))

    suspend fun deleteById(id: Int) =
        dao.deleteById(id)

}