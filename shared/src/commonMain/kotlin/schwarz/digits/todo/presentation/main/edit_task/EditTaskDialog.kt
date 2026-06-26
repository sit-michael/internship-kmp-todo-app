package schwarz.digits.todo.presentation.main.edit_task

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import schwarz.digits.todo.domain.model.Task
import schwarz.digits.todo.presentation.theme.AppColor

@Composable
fun EditTaskDialog(
    task: Task,
    onDismiss: () -> Unit,
    onSave: (Task) -> Unit
) {
    //TODO(ML): (Ticket BUGT-2325) switch description and title
    var titleInput by remember { mutableStateOf(task.description) }
    var descriptionInput by remember { mutableStateOf(task.title) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = AppColor.Background),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Edit Task",
                    style = MaterialTheme.typography.headlineLarge.copy(color = AppColor.White)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = titleInput,
                    onValueChange = { titleInput = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = AppColor.White,
                        unfocusedTextColor = AppColor.White
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    value = descriptionInput,
                    onValueChange = { descriptionInput = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = AppColor.White,
                        unfocusedTextColor = AppColor.White
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = AppColor.White)
                    }
                    ElevatedButton(
                        onClick = {
                            val edited = task.copy(
                                title = titleInput,
                                description = descriptionInput
                            )
                            onSave(edited)
                            onDismiss()
                        },
                        colors = ButtonDefaults.elevatedButtonColors(containerColor = AppColor.Primary)
                    ) {
                        Text("Save", color = AppColor.Background)
                    }
                }
            }
        }
    }
}
