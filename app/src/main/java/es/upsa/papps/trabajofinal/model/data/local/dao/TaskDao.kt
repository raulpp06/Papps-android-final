package es.upsa.papps.trabajofinal.model.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.upsa.papps.trabajofinal.model.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tasks: List<TaskEntity>)

    @Query("SELECT * FROM tasks ORDER BY dueDate ASC")
    fun getAllFlow(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks ORDER BY dueDate ASC")
    suspend fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getById(id: Int): TaskEntity?

    @Query("SELECT * FROM tasks WHERE subjectId = :subjectId ORDER BY dueDate ASC")
    fun getBySubjectFlow(subjectId: Int): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE subjectId IS NULL ORDER BY dueDate ASC")
    fun getWithNoSubjectFlow(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE isCompleted = :completed ORDER BY dueDate ASC")
    fun getByCompletedFlow(completed: Boolean): Flow<List<TaskEntity>>

    @Query(
        """
        SELECT * FROM tasks
        WHERE (:subjectId IS NULL OR subjectId = :subjectId)
          AND (:isCompleted IS NULL OR isCompleted = :isCompleted)
          AND (:fromDate IS NULL OR dueDate >= :fromDate)
          AND (:toDate IS NULL OR dueDate <= :toDate)
        ORDER BY dueDate ASC
    """
    )
    fun getFilteredFlow(
        subjectId: Int?,
        isCompleted: Boolean?,
        fromDate: LocalDate?,
        toDate: LocalDate?,
    ): Flow<List<TaskEntity>>

    @Update
    suspend fun update(task: TaskEntity)

    @Query("UPDATE tasks SET isCompleted = :completed WHERE id = :id")
    suspend fun setCompleted(id: Int, completed: Boolean)

    @Delete
    suspend fun delete(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()
}