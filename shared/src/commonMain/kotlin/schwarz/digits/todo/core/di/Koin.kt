package schwarz.digits.todo.core.di

import org.koin.core.module.Module
import org.koin.dsl.module
import schwarz.digits.todo.data.datasource.LocalTaskDataSource
import schwarz.digits.todo.data.datasource.LocalStorageTaskDataSource
import schwarz.digits.todo.data.repository.TaskRepositoryImpl
import schwarz.digits.todo.domain.repository.TaskRepository
import schwarz.digits.todo.presentation.viewmodel.TaskViewModel

expect val platformModule: Module

val sharedModule = module {
    single<LocalTaskDataSource> { LocalStorageTaskDataSource(get()) }
    single<TaskRepository> { TaskRepositoryImpl(get()) }
    single { TaskViewModel(get()) }
}
