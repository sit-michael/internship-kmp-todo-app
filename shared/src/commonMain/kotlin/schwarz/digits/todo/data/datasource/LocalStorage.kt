package schwarz.digits.todo.data.datasource

expect class LocalStorage {
    fun writeText(fileName: String, text: String)
    fun readText(fileName: String): String?
}
