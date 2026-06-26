package schwarz.digits.todo.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val CustomTypography = Typography(
    headlineLarge = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = AppColor.White
    ),
    headlineMedium = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
        color = AppColor.Grey600
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = AppColor.Grey400
    ),
    bodyMedium = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = AppColor.Grey600
    ),
    labelMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = AppColor.Grey400
    )
)

private val ColorScheme = lightColorScheme(
    primary = AppColor.Primary,
    onPrimary = AppColor.Background,
    secondary = AppColor.Background,
    onSecondary = AppColor.White,
    background = AppColor.Background,
    onBackground = AppColor.White,
    surface = AppColor.Background,
    onSurface = AppColor.White,
    error = AppColor.Error,
    onError = AppColor.White
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = CustomTypography,
        content = content
    )
}
