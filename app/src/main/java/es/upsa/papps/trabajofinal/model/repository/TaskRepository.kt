package es.upsa.papps.trabajofinal.model.repository

import es.upsa.papps.trabajofinal.model.data.Task
import es.upsa.papps.trabajofinal.model.data.TaskFilters
import es.upsa.papps.trabajofinal.model.data.filter.TaskStatusFilter
import es.upsa.papps.trabajofinal.model.data.filter.TaskSubjectFilter
import es.upsa.papps.trabajofinal.model.data.local.dao.TaskDao
import es.upsa.papps.trabajofinal.model.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val dao: TaskDao) {

    suspend fun insert(task: Task): Long =
        dao.insert(TaskEntity.fromDomain(task))

    suspend fun insertAll(tasks: List<Task>) =
        dao.insertAll(tasks.map { TaskEntity.fromDomain(it) })

    fun getAllFlow(): Flow<List<Task>> =
        dao.getAllFlow().map { list -> list.map { it.toDomain() } }

    suspend fun getAll(): List<Task> =
        dao.getAll().map { it.toDomain() }

    suspend fun getById(id: Int): Task? =
        dao.getById(id)?.toDomain()

    fun getFilteredFlow(filters: TaskFilters): Flow<List<Task>> {
        val subjectId: Int? = when (val s = filters.subject) {
            is TaskSubjectFilter.All -> null
            is TaskSubjectFilter.NoSubject -> -1
            is TaskSubjectFilter.Subject -> s.subjectId
        }

        val isCompleted: Boolean? = when (filters.status) {
            TaskStatusFilter.ALL -> null
            TaskStatusFilter.PENDING -> false
            TaskStatusFilter.COMPLETED -> true
        }

        if (filters.subject is TaskSubjectFilter.NoSubject) {
            return dao.getWithNoSubjectFlow()
                .map { list ->
                    list.filter { task ->
                        val statusOk = isCompleted == null || task.isCompleted == isCompleted
                        val fromOk =
                            filters.fromDate == null || !task.dueDate.isBefore(filters.fromDate)
                        val toOk = filters.toDate == null || !task.dueDate.isAfter(filters.toDate)
                        statusOk && fromOk && toOk
                    }.map { it.toDomain() }
                }
        }

        return dao.getFilteredFlow(
            subjectId = subjectId,
            isCompleted = isCompleted,
            fromDate = filters.fromDate,
            toDate = filters.toDate,
        ).map { list -> list.map { it.toDomain() } }
    }

    suspend fun update(task: Task) =
        dao.update(TaskEntity.fromDomain(task))

    suspend fun setCompleted(id: Int, completed: Boolean) =
        dao.setCompleted(id, completed)

    suspend fun delete(task: Task) =
        dao.delete(TaskEntity.fromDomain(task))

    suspend fun deleteById(id: Int) =
        dao.deleteById(id)

    suspend fun deleteAll() =
        dao.deleteAll()
}