package es.upsa.papps.trabajofinal.model.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.upsa.papps.trabajofinal.model.data.local.dao.ExamDao
import es.upsa.papps.trabajofinal.model.data.local.dao.SubjectDao
import es.upsa.papps.trabajofinal.model.data.local.dao.TaskDao
import es.upsa.papps.trabajofinal.model.data.local.entity.ExamEntity
import es.upsa.papps.trabajofinal.model.data.local.entity.SubjectEntity
import es.upsa.papps.trabajofinal.model.data.local.entity.TaskEntity

@Database(
    entities = [SubjectEntity::class, TaskEntity::class, ExamEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao
    abstract fun taskDao(): TaskDao
    abstract fun examDao(): ExamDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "trabajofinal.db"
                ).build().also { INSTANCE = it }
            }
    }
}