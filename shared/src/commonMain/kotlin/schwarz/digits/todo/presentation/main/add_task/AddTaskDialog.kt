package schwarz.digits.todo.presentation.main.add_task

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import schwarz.digits.todo.core.services.DateFormatter
import schwarz.digits.todo.domain.model.Task
import schwarz.digits.todo.presentation.theme.AppColor

@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    onSave: (Task) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = AppColor.Background),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Add Task",
                    style = MaterialTheme.typography.headlineLarge.copy(color = AppColor.White)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = AppColor.White,
                        unfocusedTextColor = AppColor.White
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    value = description,
                    onValueChange = { description = it },
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
                            if (title.isNotBlank()) {
                                onSave(Task(title = title, description = description, date = DateFormatter.now()))
                                onDismiss()
                            }
                        },
                        colors = ButtonDefaults.elevatedButtonColors(containerColor = AppColor.Primary)
                    ) {
                        Text("Add", color = AppColor.Background)
                    }
                }
            }
        }
    }
}
