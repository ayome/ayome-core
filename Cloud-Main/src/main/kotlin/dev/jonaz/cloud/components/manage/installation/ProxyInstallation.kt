package dev.jonaz.cloud.components.manage.installation

import dev.jonaz.cloud.components.manage.ProxyManager
import dev.jonaz.cloud.components.manage.setup.ProxySetup
import dev.jonaz.cloud.model.DatabaseModel
import dev.jonaz.cloud.util.docker.container.DockerContainer
import dev.jonaz.cloud.util.system.ErrorLogging
import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.SystemRuntime
import dev.jonaz.cloud.util.system.filesystem.DirectoryManager
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class ProxyInstallation(_name: String, _memory: Long, _port: Int) {
    private val port = _port
    private val memory = _memory
    private val singleName = _name
    private val finalName = "cloud-proxy-$singleName"
    private val path = SystemPathManager.build("proxy", singleName)


    fun start(): Boolean {
        DirectoryManager().create(path)
        ProxySetup(singleName).setupFiles()
        ProxyManager().setSubServers(singleName)
        DockerContainer().delete(finalName)
        insertDatabase()

        SystemRuntime().exec("docker", "pull", "jonaznas/bungeecord:latest")

        val fullPath = "\"$path\""
        println(fullPath)
        val result = SystemRuntime().exec(
            "docker run",
            "-d -i",
            "--name $finalName",
            "-v $fullPath:/server/data",
            "-m $memory",
            "-p $port:25577",
            "jonaznas/bungeecord:latest"
        )

        logErrors(result.second)

        return when (DockerContainer().exist(finalName)) {
            true -> true
            else -> {
                SystemRuntime.logger.error("Installation of $finalName failed")
                false
            }
        }
    }

    private fun insertDatabase() = transaction {
        val model = DatabaseModel.Proxy

        model.deleteWhere { model.name eq singleName }
        model.insert {
            it[model.name] = singleName
        }
    }

    private fun logErrors(list: List<String>) {
        if (list.isNotEmpty()) {
            val text = list.joinToString("\n")
            ErrorLogging().append(text)
        }
    }
}
