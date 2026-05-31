package es.upsa.papps.trabajofinal.model.data.filter


sealed class TaskSubjectFilter {

    data object All : TaskSubjectFilter()

    data object NoSubject : TaskSubjectFilter()

    data class Subject(val subjectId: Int) : TaskSubjectFilter()
}