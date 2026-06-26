package schwarz.digits.todo.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import schwarz.digits.todo.presentation.theme.AppColor
import schwarz.digits.todo.presentation.viewmodel.TaskUIState
import todo.shared.generated.resources.Res
import todo.shared.generated.resources.logo

@Composable
fun CustomDrawerContent(
    uiState: TaskUIState,
    onNavigateToTasks: () -> Unit,
    onNavigateToBin: () -> Unit,
    currentScreen: String
) {
    ModalDrawerSheet(
        drawerContainerColor = AppColor.Background,
        modifier = Modifier.width(280.dp).fillMaxHeight()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(AppColor.Background),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.height(80.dp).padding(8.dp)
                )
            }

            NavigationDrawerItem(
                label = { Text("My Tasks", color = AppColor.White) },
                icon = { Icon(Icons.Default.Star, contentDescription = null, tint = AppColor.Primary) },
                badge = {
                    Text(
                        text = "${uiState.pendingTasks.size} | ${uiState.completedTasks.size}",
                        color = AppColor.Grey300,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                selected = currentScreen == "tasks",
                onClick = onNavigateToTasks,
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = AppColor.Primary.copy(alpha = 0.2f),
                    unselectedContainerColor = AppColor.Background
                ),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )

            HorizontalDivider(color = AppColor.Grey600, thickness = 0.5.dp)

            NavigationDrawerItem(
                label = { Text("Bin", color = AppColor.White) },
                icon = { Icon(Icons.Default.Delete, contentDescription = null, tint = AppColor.Error) },
                badge = {
                    Text(
                        text = "${uiState.removedTasks.size}",
                        color = AppColor.Grey300,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                selected = currentScreen == "bin",
                onClick = onNavigateToBin,
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = AppColor.Primary.copy(alpha = 0.2f),
                    unselectedContainerColor = AppColor.Background
                ),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )

            HorizontalDivider(color = AppColor.Grey600, thickness = 0.5.dp)

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                VersionLabel(modifier = Modifier.wrapContentSize())
            }
        }
    }
}
