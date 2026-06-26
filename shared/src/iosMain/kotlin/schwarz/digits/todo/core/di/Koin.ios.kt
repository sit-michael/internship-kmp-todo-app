package schwarz.digits.todo.core.di

import org.koin.core.module.Module
import org.koin.dsl.module
import schwarz.digits.todo.data.datasource.LocalStorage

actual val platformModule: Module = module {
    single { LocalStorage() }
}
