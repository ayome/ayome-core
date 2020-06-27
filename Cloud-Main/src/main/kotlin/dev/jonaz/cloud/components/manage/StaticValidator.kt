package dev.jonaz.cloud.components.manage

import dev.jonaz.cloud.model.DatabaseModel
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class StaticValidator : DatabaseModel() {

    fun validateName(name: String): Pair<Boolean, String?> {
        if (Regex("[a-zA-Z0-9-_]+\$").matches(name).not()) {
            return Pair(false, "Name can only contain alphanumeric and dashes")
        }

        when (name.length) {
            in 16..Int.MAX_VALUE -> return Pair(false, "Name cannot be longer than 16 characters")
            in Int.MIN_VALUE..3 -> return Pair(false, "Name cannot be shorter than 4 characters")
        }

        if(exist(name)) {
            return Pair(false, "The name already exist")
        }

        return Pair(true, null)
    }

    private fun exist(name: String) = transaction {
        Static.select { Static.name eq name }.toList().isNotEmpty()
    }
}
