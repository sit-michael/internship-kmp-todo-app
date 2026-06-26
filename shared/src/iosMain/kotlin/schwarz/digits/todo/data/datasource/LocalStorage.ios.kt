package schwarz.digits.todo.data.datasource

import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.writeToFile
import platform.Foundation.stringWithContentsOfFile

import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
actual class LocalStorage {
    private fun getFilePath(fileName: String): String {
        val paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true)
        val documentsDirectory = paths.first() as String
        return "$documentsDirectory/$fileName"
    }

    actual fun writeText(fileName: String, text: String) {
        val filePath = getFilePath(fileName)
        val nsString = text as NSString
        nsString.writeToFile(filePath, true, NSUTF8StringEncoding, null)
    }

    actual fun readText(fileName: String): String? {
        val filePath = getFilePath(fileName)
        if (!NSFileManager.defaultManager.fileExistsAtPath(filePath)) return null
        val content = NSString.stringWithContentsOfFile(filePath, NSUTF8StringEncoding, null)
        return content as String?
    }
}
