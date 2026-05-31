package es.upsa.papps.trabajofinal.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.model.data.TaskBase
import java.time.LocalDate
import java.time.LocalTime

class TaskFormViewModel : ViewModel() {

    var isExam by mutableStateOf(false)

    var title by mutableStateOf("")
    var subjectId by mutableStateOf<Int?>(null)

    var dueDateText by mutableStateOf("")
    var examHourText by mutableStateOf("")

    var examRoom by mutableStateOf("")
    var gradePercent by mutableStateOf("")

    val dueDate: LocalDate?
        get() = runCatching { LocalDate.parse(dueDateText) }.getOrNull()

    val examHour: LocalTime?
        get() = runCatching { LocalTime.parse(examHourText) }.getOrNull()

    fun reset() {
        isExam = false
        title = ""
        subjectId = null
        dueDateText = ""
        examHourText = ""
        examRoom = ""
        gradePercent = ""
    }

    fun load(task: TaskBase) {
        title = task.title
        subjectId = task.subjectId
        dueDateText = task.dueDate.toString()

        isExam = task is Exam

        if (task is Exam) {
            examRoom = task.examRoom
            examHourText = task.examHour.toString()
            gradePercent = task.gradePercent.toString()
        }
    }

    fun isDueDateValid() =
        runCatching {
            LocalDate.parse(dueDateText)
        }.isSuccess

    fun isExamHourValid() =
        runCatching {
            LocalTime.parse(examHourText)
        }.isSuccess

    fun isValid(): Boolean {
        if (title.isBlank() || !isDueDateValid()) return false

        return if (isExam) {
            examRoom.isNotBlank() &&
                    isExamHourValid() &&
                    subjectId != null &&
                    gradePercent.toFloatOrNull()?.let { it in 0f..1f } == true
        } else {
            true
        }
    }
}