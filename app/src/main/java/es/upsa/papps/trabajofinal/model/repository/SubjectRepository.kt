package es.upsa.papps.trabajofinal.model.repository

import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.model.data.local.dao.SubjectDao
import es.upsa.papps.trabajofinal.model.data.local.entity.SubjectEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubjectRepository(private val dao: SubjectDao) {

    suspend fun insert(subject: Subject): Long =
        dao.insert(SubjectEntity.fromDomain(subject))

    suspend fun insertAll(subjects: List<Subject>) =
        dao.insertAll(subjects.map { SubjectEntity.fromDomain(it) })

    fun getAllFlow(): Flow<List<Subject>> =
        dao.getAllFlow().map { list -> list.map { it.toDomain() } }

    suspend fun getAll(): List<Subject> =
        dao.getAll().map { it.toDomain() }

    suspend fun getById(id: Int): Subject? =
        dao.getById(id)?.toDomain()

    fun getByIdFlow(id: Int): Flow<Subject?> =
        dao.getByIdFlow(id).map { it?.toDomain() }

    suspend fun update(subject: Subject) =
        dao.update(SubjectEntity.fromDomain(subject))

    suspend fun delete(subject: Subject) =
        dao.delete(SubjectEntity.fromDomain(subject))

    suspend fun deleteById(id: Int) =
        dao.deleteById(id)

    suspend fun deleteAll() =
        dao.deleteAll()
}