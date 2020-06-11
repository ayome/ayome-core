package dev.jonaz.cloud.components.setup

import dev.jonaz.cloud.model.DatabaseModel
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseSetup : DatabaseModel() {

    fun isExist(): Boolean {
        val setupFinished = transaction {
            Application.select { name eq "setupFinished" }.toList()
        }

        return when {
            setupFinished.size != 1 -> false
            setupFinished[0][value] == "true" -> true
            else -> false
        }
    }

    fun finishSetup() = transaction {
        Application.insert {
            it[name] = "setupFinished"
            it[value] = "true"
        }
    }
}
