package dev.jonaz.cloud.components.manage

import dev.jonaz.cloud.model.DatabaseModel
import dev.jonaz.cloud.model.server.ServerModel
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ServerManager : DatabaseModel() {

    fun getAll() = transaction {
        val servers: MutableList<ServerModel> = mutableListOf()

        Static.selectAll().toList().forEach { t ->
            val name = "cloud-static-${t[Static.name]}"
            val model = ServerModel(name)

            servers.add(model)
        }

        return@transaction servers
    }
}
