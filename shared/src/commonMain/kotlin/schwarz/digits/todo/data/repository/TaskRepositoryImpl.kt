package schwarz.digits.todo.data.repository

import schwarz.digits.todo.data.datasource.LocalTaskDataSource
import schwarz.digits.todo.data.model.TaskModel
import schwarz.digits.todo.domain.model.Task
import schwarz.digits.todo.domain.repository.TaskRepository

class TaskRepositoryImpl(private val local: LocalTaskDataSource) : TaskRepository {
    override suspend fun create(task: Task) {
        local.create(TaskModel.fromDomain(task))
    }

    override suspend fun get(): List<Task> {
        return local.get().map { it.toDomain() }
    }

    override suspend fun update(task: Task) {
        local.update(TaskModel.fromDomain(task))
    }

    override suspend fun delete(task: Task) {
        local.delete(TaskModel.fromDomain(task))
    }
}
