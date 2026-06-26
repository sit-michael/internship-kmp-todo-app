package schwarz.digits.todo.domain.repository

import schwarz.digits.todo.domain.model.Task

interface TaskRepository {
    suspend fun create(task: Task)
    suspend fun get(): List<Task>
    suspend fun update(task: Task)
    suspend fun delete(task: Task)
}
