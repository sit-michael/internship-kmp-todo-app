package schwarz.digits.todo.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import schwarz.digits.todo.presentation.theme.AppColor

@Composable
fun SplashScreen(onNextPage: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNextPage()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = AppColor.Primary)
    }
}
