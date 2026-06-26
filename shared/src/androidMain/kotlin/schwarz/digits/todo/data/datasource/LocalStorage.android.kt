package schwarz.digits.todo.data.datasource

import android.content.Context
import java.io.File

actual class LocalStorage(private val context: Context) {
    actual fun writeText(fileName: String, text: String) {
        val file = File(context.filesDir, fileName)
        file.writeText(text)
    }

    actual fun readText(fileName: String): String? {
        val file = File(context.filesDir, fileName)
        return if (file.exists()) file.readText() else null
    }
}
