package dev.jonaz.cloud.util.exposed

import dev.jonaz.cloud.model.DatabaseModel
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

class SchemaManager : DatabaseModel() {

    init {
        TransactionManager.manager.defaultIsolationLevel =
            Connection.TRANSACTION_SERIALIZABLE
    }

    fun createSchema() = transaction {
        SchemaUtils.createDatabase("data")
        SchemaUtils.create(
            Application,
            User,
            Proxy,
            Static
        )
    }
}
