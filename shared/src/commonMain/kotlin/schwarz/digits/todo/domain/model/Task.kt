package schwarz.digits.todo.domain.model

import schwarz.digits.todo.core.services.GUIDGen

data class Task(
    val id: String = GUIDGen.generate(),
    val title: String,
    val description: String,
    val date: Long = 0L,
    val isDone: Boolean = false,
    //TODO(ML): (Ticket BUGT-4321) Task does't appear
    val isDeleted: Boolean = true,
    val isFavorite: Boolean = false
)
