package schwarz.digits.todo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform