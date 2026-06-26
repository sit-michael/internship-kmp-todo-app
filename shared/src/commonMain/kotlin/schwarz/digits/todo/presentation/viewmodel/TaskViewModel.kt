package schwarz.digits.todo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import schwarz.digits.todo.domain.model.Task
import schwarz.digits.todo.domain.repository.TaskRepository

data class TaskUIState(
    val allTasks: List<Task> = emptyList()
) {
    val pendingTasks: List<Task> get() = allTasks.filter { !it.isDone && !it.isDeleted }
    val completedTasks: List<Task> get() = allTasks.filter { it.isDone && !it.isDeleted }
    val favoriteTasks: List<Task> get() = allTasks.filter { it.isFavorite && !it.isDeleted }
    val removedTasks: List<Task> get() = allTasks.filter { it.isDeleted }
}

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskUIState())
    val uiState: StateFlow<TaskUIState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            val tasks = repository.get()
            _uiState.value = TaskUIState(allTasks = tasks)
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.create(task)
            loadTasks()
        }
    }

    fun editTask(task: Task) {
        viewModelScope.launch {
            repository.update(task)
            loadTasks()
        }
    }

    fun toggleTaskDone(task: Task) {
        viewModelScope.launch {
            repository.update(task.copy(isDone = !task.isDone))
            loadTasks()
        }
    }

    fun toggleTaskFavorite(task: Task) {
        viewModelScope.launch {
            repository.update(task.copy(isFavorite = !task.isFavorite))
            loadTasks()
        }
    }

    fun removeTask(task: Task) {
        viewModelScope.launch {
            repository.update(task.copy(isDeleted = true))
            loadTasks()
        }
    }

    fun restoreTask(task: Task) {
        viewModelScope.launch {
            repository.update(task.copy(isDeleted = false, isDone = false, isFavorite = false))
            loadTasks()
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.delete(task)
            loadTasks()
        }
    }

    fun deleteAllRemovedTasks() {
        viewModelScope.launch {
            val removed = _uiState.value.removedTasks
            removed.forEach { repository.delete(it) }
            loadTasks()
        }
    }
}
