package schwarz.digits.todo.core.services

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object GUIDGen {
    @OptIn(ExperimentalUuidApi::class)
    fun generate(): String {
        return Uuid.random().toString()
    }
}
