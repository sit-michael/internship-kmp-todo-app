package schwarz.digits.todo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import schwarz.digits.todo.presentation.main.TabsScreen
import schwarz.digits.todo.presentation.recycle_bin.RecycleBinScreen
import schwarz.digits.todo.presentation.splash.SplashScreen
import schwarz.digits.todo.presentation.theme.AppTheme
import schwarz.digits.todo.presentation.viewmodel.TaskViewModel

enum class Screen {
    Splash,
    Tabs,
    RecycleBin
}

@Composable
fun App() {
    AppTheme {
        val viewModel: TaskViewModel = koinInject()
        val uiState by viewModel.uiState.collectAsState()
        var currentScreen by remember { mutableStateOf(Screen.Splash) }
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            when (currentScreen) {
                Screen.Splash -> {
                    SplashScreen(
                        onNextPage = { currentScreen = Screen.Tabs }
                    )
                }
                Screen.Tabs -> {
                    TabsScreen(
                        viewModel = viewModel,
                        uiState = uiState,
                        onNavigateToBin = { currentScreen = Screen.RecycleBin },
                        showSnackbar = { msg ->
                            scope.launch {
                                snackbarHostState.showSnackbar(msg)
                            }
                        }
                    )
                }
                Screen.RecycleBin -> {
                    RecycleBinScreen(
                        viewModel = viewModel,
                        uiState = uiState,
                        onNavigateToTasks = { currentScreen = Screen.Tabs }
                    )
                }
            }
        }
    }
}