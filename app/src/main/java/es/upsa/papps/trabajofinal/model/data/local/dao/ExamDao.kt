package es.upsa.papps.trabajofinal.model.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.upsa.papps.trabajofinal.model.data.local.entity.ExamEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface ExamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exam: ExamEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exams: List<ExamEntity>)

    @Query("SELECT * FROM exams ORDER BY dueDate ASC")
    fun getAllFlow(): Flow<List<ExamEntity>>

    @Query("SELECT * FROM exams ORDER BY dueDate ASC")
    suspend fun getAll(): List<ExamEntity>

    @Query("SELECT * FROM exams WHERE id = :id")
    suspend fun getById(id: Int): ExamEntity?

    @Query("SELECT * FROM exams WHERE subjectId = :subjectId ORDER BY dueDate ASC")
    fun getBySubjectFlow(subjectId: Int): Flow<List<ExamEntity>>

    @Query("SELECT * FROM exams WHERE isCompleted = :completed ORDER BY dueDate ASC")
    fun getByCompletedFlow(completed: Boolean): Flow<List<ExamEntity>>

    @Query(
        """
        SELECT * FROM exams
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
    ): Flow<List<ExamEntity>>

    @Update
    suspend fun update(exam: ExamEntity)

    @Query("UPDATE exams SET isCompleted = :completed WHERE id = :id")
    suspend fun setCompleted(id: Int, completed: Boolean)

    @Delete
    suspend fun delete(exam: ExamEntity)

    @Query("DELETE FROM exams WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM exams")
    suspend fun deleteAll()
}