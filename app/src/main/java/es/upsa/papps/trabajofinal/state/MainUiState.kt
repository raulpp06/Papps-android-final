package es.upsa.papps.trabajofinal.state

import es.upsa.papps.trabajofinal.model.data.Subject
import es.upsa.papps.trabajofinal.model.data.TaskBase

data class MainUiState(
    val subjects: List<Subject>,
    val tasks: List<TaskBase>,
) {
    // Si añado propiedades no olvidar
    fun isEmpty(): Boolean = subjects.isEmpty() && tasks.isEmpty()
}
