package es.upsa.papps.trabajofinal.model.repository

import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.model.data.local.dao.SubjectDao
import es.upsa.papps.trabajofinal.model.data.local.entity.SubjectEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubjectRepository(private val dao: SubjectDao) {

    suspend fun insert(subject: Subject): Long =
        dao.insert(SubjectEntity.fromDomain(subject))

    fun getAllFlow(): Flow<List<Subject>> =
        dao.getAllFlow().map { list -> list.map { it.toDomain() } }

    suspend fun update(subject: Subject) =
        dao.update(SubjectEntity.fromDomain(subject))

    suspend fun delete(subject: Subject) =
        dao.delete(SubjectEntity.fromDomain(subject))

    suspend fun deleteById(id: Int) =
        dao.deleteById(id)

}