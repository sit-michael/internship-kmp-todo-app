package schwarz.digits.todo.presentation.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import schwarz.digits.todo.domain.model.Task

@Composable
fun TaskList(
    taskList: List<Task>,
    onDoneChanged: (Task, Boolean, String) -> Unit,
    onEditClicked: (Task) -> Unit,
    onDeleteClicked: (Task) -> Unit,
    onRestoreClicked: (Task) -> Unit,
    onFavoriteClicked: (Task) -> Unit,
    showBinActions: Boolean = false
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(taskList, key = { it.id }) { task ->
            TaskTile(
                task = task,
                onDoneChanged = { checked, text -> onDoneChanged(task, checked, text) },
                onEditClicked = { onEditClicked(task) },
                onDeleteClicked = { onDeleteClicked(task) },
                onRestoreClicked = { onRestoreClicked(task) },
                onFavoriteClicked = { onFavoriteClicked(task) },
                showBinActions = showBinActions
            )
        }
    }
}
