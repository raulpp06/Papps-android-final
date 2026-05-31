package es.upsa.papps.trabajofinal.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import es.upsa.papps.trabajofinal.model.data.Subject

class SubjectFormViewModel : ViewModel() {
    var name by mutableStateOf("")
    var teacher by mutableStateOf("")
    var credits by mutableStateOf("")

    private val creditsInt
        get() = credits.toIntOrNull()

    private var initialized = false

    fun loadSubject(subject: Subject) {
        if (initialized) return

        name = subject.name
        teacher = subject.teacher
        credits = subject.credits.toString()

        initialized = true
    }

    fun isCreditsValid() = credits.toIntOrNull()?.let { it > 0 } ?: false

    fun isValid() = name.isNotBlank() && teacher.isNotBlank() && isCreditsValid()
}
