package schwarz.digits.todo

import org.koin.core.context.startKoin
import schwarz.digits.todo.core.di.platformModule
import schwarz.digits.todo.core.di.sharedModule

fun initKoin() {
    startKoin {
        modules(platformModule, sharedModule)
    }
}
