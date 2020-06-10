package dev.jonaz.cloud.util.exposed

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

class SchemaManager {

    init {
        TransactionManager.manager.defaultIsolationLevel =
            Connection.TRANSACTION_SERIALIZABLE
    }

    fun createSchema() = transaction {
        SchemaUtils.createDatabase("data")
    }
}
