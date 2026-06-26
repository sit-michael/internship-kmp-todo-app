package schwarz.digits.todo.data.datasource

import kotlinx.serialization.json.Json
import kotlinx.serialization.builtins.ListSerializer
import schwarz.digits.todo.data.model.TaskModel

class LocalStorageTaskDataSource(private val storage: LocalStorage) : LocalTaskDataSource {
    private val fileName = "tasks.json"
    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

    private fun loadTasks(): MutableList<TaskModel> {
        val text = storage.readText(fileName) ?: return mutableListOf()
        return try {
            json.decodeFromString(ListSerializer(TaskModel.serializer()), text).toMutableList()
        } catch (e: Exception) {
            mutableListOf()
        }
    }

    private fun saveTasks(tasks: List<TaskModel>) {
        val text = json.encodeToString(ListSerializer(TaskModel.serializer()), tasks)
        storage.writeText(fileName, text)
    }

    override suspend fun create(model: TaskModel) {
        val tasks = loadTasks()
        tasks.add(model)
        saveTasks(tasks)
    }

    override suspend fun get(): List<TaskModel> {
        return loadTasks()
    }

    override suspend fun update(model: TaskModel) {
        val tasks = loadTasks()
        val index = tasks.indexOfFirst { it.id == model.id }
        if (index != -1) {
            tasks[index] = model
        } else {
            tasks.add(model)
        }
        saveTasks(tasks)
    }

    override suspend fun delete(model: TaskModel) {
        val tasks = loadTasks()
        tasks.removeAll { it.id == model.id }
        saveTasks(tasks)
    }
}
