package schwarz.digits.todo.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import schwarz.digits.todo.core.services.DateFormatter
import schwarz.digits.todo.domain.model.Task
import schwarz.digits.todo.presentation.theme.AppColor

@Composable
fun TaskTile(
    task: Task,
    onDoneChanged: (Boolean, String) -> Unit,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onRestoreClicked: () -> Unit,
    onFavoriteClicked: () -> Unit,
    showBinActions: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = AppColor.Grey600.copy(alpha = 0.2f)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = task.isDone,
                    onCheckedChange = { checked ->
                        //TODO(ML) : (Ticket BUGT-1500) Wrong snackbar message
                        val snackbarText = if (checked) {
                            "Resetted Task"
                        } else {
                            "Finished Task"
                        }
                        onDoneChanged(checked, snackbarText)
                    },
                    enabled = !task.isDeleted
                )

                //TODO(ML): (Ticket FXT-106) Add Fav icon

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = AppColor.White,
                            textDecoration = if (task.isDone) TextDecoration.LineThrough else null
                        )
                    )
                    Text(
                        text = DateFormatter.format(task.date),
                        style = MaterialTheme.typography.bodyMedium.copy(color = AppColor.Grey300)
                    )
                }

                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "Actions",
                            tint = AppColor.White
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        modifier = Modifier.background(AppColor.Background)
                    ) {
                        if (!showBinActions) {
                            DropdownMenuItem(
                                text = { Text("Edit", color = AppColor.White) },
                                onClick = {
                                    menuExpanded = false
                                    onEditClicked()
                                }
                            )

                            //TODO(ML): (Ticket FXT-106) Add Favourites function and icon

                            DropdownMenuItem(
                                text = { Text("Delete", color = AppColor.Error) },
                                onClick = {
                                    menuExpanded = false
                                    onDeleteClicked()
                                }
                            )
                        } else {
                            DropdownMenuItem(
                                text = { Text("Restore", color = AppColor.White) },
                                onClick = {
                                    menuExpanded = false
                                    onRestoreClicked()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Delete Forever", color = AppColor.Error) },
                                onClick = {
                                    menuExpanded = false
                                    onDeleteClicked()
                                }
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp, start = 48.dp)) {
                    HorizontalDivider(color = AppColor.Grey600, thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Description:",
                        style = MaterialTheme.typography.bodyLarge.copy(color = AppColor.Primary)
                    )
                    Text(
                        text = task.description.ifEmpty { "No description" },
                        style = MaterialTheme.typography.bodyLarge.copy(color = AppColor.White)
                    )
                }
            }
        }
    }
}
