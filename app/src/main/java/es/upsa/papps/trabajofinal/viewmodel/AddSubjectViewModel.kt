package es.upsa.papps.trabajofinal.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddSubjectViewModel : ViewModel() {

    var name by mutableStateOf("")
    var teacher by mutableStateOf("")
    var credits by mutableStateOf("")

    private val creditsInt
        get() = credits.toIntOrNull()

    fun isCreditsValid() =
        credits.toIntOrNull()?.let { it > 0 } ?: false

    fun isValid() =
        name.isNotBlank() && teacher.isNotBlank() && isCreditsValid()
}