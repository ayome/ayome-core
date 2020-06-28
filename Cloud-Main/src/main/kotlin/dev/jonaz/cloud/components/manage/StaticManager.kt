package dev.jonaz.cloud.components.manage

import dev.jonaz.cloud.model.DatabaseModel
import dev.jonaz.cloud.model.server.StaticServerModel
import dev.jonaz.cloud.util.docker.container.DockerContainer
import dev.jonaz.cloud.util.system.ErrorLogging
import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.SystemRuntime
import dev.jonaz.cloud.util.system.filesystem.DirectoryManager
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class StaticManager : DatabaseModel() {

    fun getAll() = transaction {
        val servers = mutableListOf<StaticServerModel>()

        Static.selectAll().forEach { t ->
            val model = StaticServerModel(
                t[Static.name], null
            )

            servers.add(model)
        }

        return@transaction servers
    }

    fun installStatic(name: String, memory: Long, port: Int, version: String): Boolean {
        SystemRuntime.logger.info("Starting installation of static named $name")

        val staticName = "cloud-static-$name"
        val path = "${SystemPathManager.current}static/$name"
        DirectoryManager().create(path)

        transaction {
            Static.deleteWhere { Static.name eq name }
            Static.insert {
                it[Proxy.name] = name
            }
        }

        DockerContainer().delete(staticName)
        val result = SystemRuntime().exec(
            "docker run",
            "-d -i",
            "--name $staticName",
            "-v \"$path\":/data",
            "-m $memory --memory-swap $memory",
            "-p $port:25565",
            "-e VERSION=\"$version\"",
            "jonaznas/papermc"
        )

        if (result.second.isNotEmpty()) {
            SystemRuntime.logger.error("Installation of static $staticName failed")
            ErrorLogging().append(result.second.joinToString("\n"))
            return true
        }

        SystemRuntime.logger.info("Installation of static $staticName completed")
        return true
    }
}
