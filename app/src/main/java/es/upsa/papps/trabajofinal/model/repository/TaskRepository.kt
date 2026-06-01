package es.upsa.papps.trabajofinal.model.repository

import es.upsa.papps.trabajofinal.model.data.Task
import es.upsa.papps.trabajofinal.model.data.local.dao.TaskDao
import es.upsa.papps.trabajofinal.model.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val dao: TaskDao) {

    suspend fun insert(task: Task): Long =
        dao.insert(TaskEntity.fromDomain(task))

    fun getAllFlow(): Flow<List<Task>> =
        dao.getAllFlow().map { list -> list.map { it.toDomain() } }

    suspend fun update(task: Task) =
        dao.update(TaskEntity.fromDomain(task))

    suspend fun setCompleted(id: Int, completed: Boolean) =
        dao.setCompleted(id, completed)

    suspend fun delete(task: Task) =
        dao.delete(TaskEntity.fromDomain(task))

    suspend fun deleteById(id: Int) =
        dao.deleteById(id)

}