package es.upsa.papps.trabajofinal.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import es.upsa.papps.trabajofinal.model.data.Exam
import es.upsa.papps.trabajofinal.model.data.TaskBase

class TaskFormViewModel : ViewModel() {

    var isExam by mutableStateOf(false)

    var title by mutableStateOf("")
    var subjectId by mutableStateOf<Int?>(null)
    var dueDate by mutableStateOf("")

    var examRoom by mutableStateOf("")
    var examHour by mutableStateOf("")
    var gradePercent by mutableStateOf("")

    fun reset() {
        isExam = false
        title = ""
        subjectId = null
        dueDate = ""
        examRoom = ""
        examHour = ""
        gradePercent = ""
    }

    fun load(task: TaskBase) {
        title = task.title
        subjectId = task.subjectId
        dueDate = task.dueDate
        isExam = task is Exam

        if (task is Exam) {
            examRoom = task.examRoom
            examHour = task.examHour
            gradePercent = task.gradePercent.toString()
        } else {
            examRoom = ""
            examHour = ""
            gradePercent = ""
        }
    }

    fun isValid(): Boolean {
        if (title.isBlank() || dueDate.isBlank()) return false

        return if (isExam) {
            examRoom.isNotBlank() &&
                    examHour.isNotBlank() &&
                    subjectId != null &&
                    gradePercent.toFloatOrNull()?.let { it in 0f..1f } == true
        } else {
            true
        }
    }
}