package schwarz.digits.todo.presentation.recycle_bin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import schwarz.digits.todo.presentation.common.TaskList
import schwarz.digits.todo.presentation.theme.AppColor
import schwarz.digits.todo.presentation.viewmodel.TaskUIState
import schwarz.digits.todo.presentation.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleBinScreen(
    viewModel: TaskViewModel,
    uiState: TaskUIState,
    onNavigateToTasks: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recycle Bin", style = MaterialTheme.typography.headlineLarge) },
                navigationIcon = {
                    IconButton(onClick = {
                        // Open drawer action - omitted on main
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = AppColor.White)
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More Actions", tint = AppColor.White)
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false },
                            modifier = Modifier.background(AppColor.Background)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Delete all tasks", color = AppColor.Error) },
                                leadingIcon = { Icon(Icons.Default.DeleteForever, contentDescription = null, tint = AppColor.Error) },
                                onClick = {
                                    menuExpanded = false
                                    viewModel.deleteAllRemovedTasks()
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColor.Background)
            )
        },
        containerColor = AppColor.Background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            SuggestionChip(
                onClick = {},
                label = { Text("${uiState.removedTasks.size} Tasks", color = AppColor.White) },
                shape = CircleShape,
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = AppColor.Grey600.copy(alpha = 0.3f)
                ),
                border = null
            )
            Spacer(modifier = Modifier.height(8.dp))
            TaskList(
                taskList = uiState.removedTasks,
                onDoneChanged = { _, _, _ -> },
                onEditClicked = {},
                onDeleteClicked = { viewModel.deleteTask(it) },
                onRestoreClicked = { viewModel.restoreTask(it) },
                onFavoriteClicked = {},
                showBinActions = true
            )
        }
    }
}
