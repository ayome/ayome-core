package dev.jonaz.cloud.components.setup

import dev.jonaz.cloud.components.user.UserCreation
import dev.jonaz.cloud.model.DatabaseModel
import dev.jonaz.cloud.util.system.SystemRuntime
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseSetup : DatabaseModel() {

    fun isExist(): Boolean {
        val setupFinished = transaction {
            Application.select { Application.name eq "setupFinished" }.toList()
        }

        return when {
            setupFinished.size != 1 -> false
            setupFinished[0][Application.value] == "true" -> true
            else -> false
        }
    }

    fun finishSetup() = transaction {
        val adminData = UserCreation().createNew("admin")
        SystemRuntime.logger.info(" ")
        SystemRuntime.logger.info("Created default login")
        SystemRuntime.logger.info("Username: admin")
        SystemRuntime.logger.info("Password: ${adminData.second}")
        SystemRuntime.logger.info(" ")
        Application.insert {
            it[name] = "setupFinished"
            it[value] = "true"
        }
    }
}
