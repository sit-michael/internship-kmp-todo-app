package schwarz.digits.todo.core.services

expect object DateFormatter {
    fun now(): Long
    fun format(epochMillis: Long): String
}
