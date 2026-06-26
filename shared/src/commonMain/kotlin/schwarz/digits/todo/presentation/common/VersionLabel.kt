package schwarz.digits.todo.presentation.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import schwarz.digits.todo.core.services.getAppVersion

@Composable
fun VersionLabel(modifier: Modifier = Modifier) {
    Text(
        text = "Schwarz IT KG\nVersion ${getAppVersion()}",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
    )
}
