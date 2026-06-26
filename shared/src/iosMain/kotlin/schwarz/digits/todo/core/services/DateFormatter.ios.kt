package schwarz.digits.todo.core.services

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.timeIntervalSince1970

actual object DateFormatter {
    actual fun now(): Long {
        return (NSDate().timeIntervalSince1970 * 1000).toLong()
    }

    actual fun format(epochMillis: Long): String {
        val date = NSDate(epochMillis.toDouble() / 1000.0)
        val formatter = NSDateFormatter().apply {
            dateFormat = "dd.MM.yyyy HH:mm"
        }
        return formatter.stringFromDate(date)
    }
}
