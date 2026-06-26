package schwarz.digits.todo.data.datasource

import schwarz.digits.todo.data.model.TaskModel

interface LocalTaskDataSource {
    suspend fun create(model: TaskModel)
    suspend fun get(): List<TaskModel>
    suspend fun update(model: TaskModel)
    suspend fun delete(model: TaskModel)
}
