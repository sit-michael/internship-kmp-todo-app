package schwarz.digits.todo.data.model

import kotlinx.serialization.Serializable
import schwarz.digits.todo.domain.model.Task

@Serializable
data class TaskModel(
    val id: String,
    val title: String,
    val description: String,
    val date: Long,
    val isDone: Boolean,
    val isDeleted: Boolean,
    val isFavorite: Boolean
) {
    fun toDomain(): Task = Task(
        id = id,
        title = title,
        description = description,
        date = date,
        isDone = isDone,
        isDeleted = isDeleted,
        isFavorite = isFavorite
    )

    companion object {
        fun fromDomain(task: Task): TaskModel = TaskModel(
            id = task.id,
            title = task.title,
            description = task.description,
            date = task.date,
            isDone = task.isDone,
            isDeleted = task.isDeleted,
            isFavorite = task.isFavorite
        )
    }
}
