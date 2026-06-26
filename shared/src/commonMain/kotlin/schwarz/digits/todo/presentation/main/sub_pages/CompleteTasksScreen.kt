package schwarz.digits.todo.presentation.main.sub_pages

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import schwarz.digits.todo.domain.model.Task
import schwarz.digits.todo.presentation.common.TaskList
import schwarz.digits.todo.presentation.theme.AppColor

@Composable
fun CompleteTasksScreen(
    taskList: List<Task>,
    onDoneChanged: (Task, Boolean, String) -> Unit,
    onEditClicked: (Task) -> Unit,
    onDeleteClicked: (Task) -> Unit,
    onFavoriteClicked: (Task) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        SuggestionChip(
            onClick = {},
            label = { Text("${taskList.size} Tasks", color = AppColor.White) },
            shape = CircleShape,
            colors = SuggestionChipDefaults.suggestionChipColors(
                containerColor = AppColor.Grey600.copy(alpha = 0.3f)
            ),
            border = null
        )
        Spacer(modifier = Modifier.height(8.dp))
        TaskList(
            taskList = taskList,
            onDoneChanged = onDoneChanged,
            onEditClicked = onEditClicked,
            onDeleteClicked = onDeleteClicked,
            onRestoreClicked = {},
            onFavoriteClicked = onFavoriteClicked,
            showBinActions = false
        )
    }
}
