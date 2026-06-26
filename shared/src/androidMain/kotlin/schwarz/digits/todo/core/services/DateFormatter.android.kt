package schwarz.digits.todo.core.services

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual object DateFormatter {
    actual fun now(): Long {
        return System.currentTimeMillis()
    }

    actual fun format(epochMillis: Long): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(epochMillis))
    }
}
