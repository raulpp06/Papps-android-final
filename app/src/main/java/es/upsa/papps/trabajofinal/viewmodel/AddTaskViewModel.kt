package es.upsa.papps.trabajofinal.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddTaskViewModel : ViewModel() {

    var isExam by mutableStateOf(false)

    var title by mutableStateOf("")
    var subjectId by mutableStateOf<Int?>(null)
    var dueDate by mutableStateOf("")

    // exam
    var examRoom by mutableStateOf("")
    var examHour by mutableStateOf("")
    var gradePercent by mutableStateOf("")

    fun isValid(): Boolean {
        if (title.isBlank() || subjectId == null || dueDate.isBlank()) return false

        return if (isExam) {
            examRoom.isNotBlank() &&
                    examHour.isNotBlank() &&
                    gradePercent.toFloatOrNull()?.let { it in 0f..1f } == true
        } else {
            true
        }
    }
}