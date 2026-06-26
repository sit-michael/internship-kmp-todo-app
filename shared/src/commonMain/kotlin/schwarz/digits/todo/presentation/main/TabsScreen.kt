package schwarz.digits.todo.presentation.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import schwarz.digits.todo.domain.model.Task
import schwarz.digits.todo.presentation.main.add_task.AddTaskDialog
import schwarz.digits.todo.presentation.main.edit_task.EditTaskDialog
import schwarz.digits.todo.presentation.main.sub_pages.CompleteTasksScreen
import schwarz.digits.todo.presentation.main.sub_pages.PendingTasksScreen
import schwarz.digits.todo.presentation.theme.AppColor
import schwarz.digits.todo.presentation.viewmodel.TaskUIState
import schwarz.digits.todo.presentation.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsScreen(
    viewModel: TaskViewModel,
    uiState: TaskUIState,
    onNavigateToBin: () -> Unit,
    showSnackbar: (String) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    var showAddDialog by remember { mutableStateOf(false) }
    var editingTask by remember { mutableStateOf<Task?>(null) }

    //TODO(ML): Add drawer (Ticket FXT-4711)

    Scaffold(
        topBar = {
            TopAppBar(
                //TODO(ML): Add AppBar title (Ticket FXT-5214)
                title = { Text("Todo", style = MaterialTheme.typography.headlineLarge) },
                navigationIcon = {
                    IconButton(onClick = {
                        // Drawer would open here - not yet implemented
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = AppColor.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppColor.Background)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = AppColor.Background) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                    label = { Text("Pending") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AppColor.Background,
                        unselectedIconColor = AppColor.Grey300,
                        selectedTextColor = AppColor.Grey200,
                        unselectedTextColor = AppColor.Grey300,
                        indicatorColor = AppColor.Primary
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Check, contentDescription = null) },
                    label = { Text("Completed") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AppColor.Background,
                        unselectedIconColor = AppColor.Grey300,
                        selectedTextColor = AppColor.Grey200,
                        unselectedTextColor = AppColor.Grey300,
                        indicatorColor = AppColor.Primary
                    )
                )
                //TODO(ML): (Ticket FXT-106) Add fav bottombar icon
            }
        },
        floatingActionButton = {
            if (selectedTab == 0) {
                FloatingActionButton(
                    //TODO(ML): Adjust FAB colour (Ticket BUGT-1734)
                    onClick = { showAddDialog = true }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task")
                }
            }
        },
        containerColor = AppColor.Background
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            //TODO(ML): (Ticket FXT-106) Add fav page
            when (selectedTab) {
                0 -> PendingTasksScreen(
                    taskList = uiState.pendingTasks,
                    onDoneChanged = { task, checked, msg ->
                        viewModel.toggleTaskDone(task)
                        showSnackbar(msg)
                    },
                    onEditClicked = { editingTask = it },
                    onDeleteClicked = { viewModel.removeTask(it) },
                    onFavoriteClicked = { viewModel.toggleTaskFavorite(it) }
                )
                1 -> CompleteTasksScreen(
                    taskList = uiState.completedTasks,
                    onDoneChanged = { task, checked, msg ->
                        viewModel.toggleTaskDone(task)
                        showSnackbar(msg)
                    },
                    onEditClicked = { editingTask = it },
                    onDeleteClicked = { viewModel.removeTask(it) },
                    onFavoriteClicked = { viewModel.toggleTaskFavorite(it) }
                )
            }
        }
    }

    if (showAddDialog) {
        AddTaskDialog(
            onDismiss = { showAddDialog = false },
            onSave = { viewModel.addTask(it) }
        )
    }

    val currentEditingTask = editingTask
    if (currentEditingTask != null) {
        EditTaskDialog(
            task = currentEditingTask,
            onDismiss = { editingTask = null },
            onSave = { viewModel.editTask(it) }
        )
    }
}
