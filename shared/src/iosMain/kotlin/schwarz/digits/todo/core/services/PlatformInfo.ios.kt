package schwarz.digits.todo.core.services

import platform.Foundation.NSBundle

actual fun getAppVersion(): String {
    val version = NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String ?: "1.0.0"
    val build = NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleVersion") as? String ?: "1"
    return "$version ($build)"
}
