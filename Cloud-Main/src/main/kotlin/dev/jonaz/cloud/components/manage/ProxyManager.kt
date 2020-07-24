package dev.jonaz.cloud.components.manage

import dev.jonaz.cloud.components.manage.config.ProxyConfigManager
import dev.jonaz.cloud.components.manage.setup.ProxySetup
import dev.jonaz.cloud.model.DatabaseModel
import dev.jonaz.cloud.model.config.proxy.ProxyConfigServerModel
import dev.jonaz.cloud.model.docker.DockerInspectModel
import dev.jonaz.cloud.util.docker.container.DockerContainer
import dev.jonaz.cloud.util.docker.container.DockerInspect
import dev.jonaz.cloud.util.docker.container.DockerRemove
import dev.jonaz.cloud.util.docker.system.DockerExec
import dev.jonaz.cloud.util.socket.SocketHandler
import dev.jonaz.cloud.util.system.ErrorLogging
import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.SystemRuntime
import dev.jonaz.cloud.util.system.filesystem.DirectoryManager
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ProxyManager : DatabaseModel() {

    fun getProxy(name: String): Pair<Boolean, DockerInspectModel?> {
        val proxies = transaction {
            Proxy.select { Proxy.name eq name }.toList()
        }

        if (proxies.isEmpty()) {
            return Pair(false, null)
        }

        val result = DockerInspect().getByName("cloud-proxy-$name")

        return Pair(result.first, result.second)
    }

    fun exec(container: String, command: String, timeout: Int = 0) {
        DockerExec().pty(container, "echo '$command' > /proc/1/fd/0", timeout)
    }

    fun installProxy(name: String, memory: Long, port: Int): Boolean {
        val proxyName = "cloud-proxy-$name"
        SystemRuntime.logger.info("Starting installation of $proxyName")

        val path = SystemPathManager.build("proxy", name)
        DirectoryManager().create(path)
        ProxySetup(name).setupFiles()
        setSubServers(name)

        transaction {
            Proxy.deleteWhere { Proxy.name eq name }
            Proxy.insert {
                it[Proxy.name] = name
            }
        }

        DockerContainer().delete(proxyName)
        SystemRuntime().exec("docker", "pull", "jonaznas/bungeecord:latest")

        val result = SystemRuntime().exec(
            "docker run",
            "-d -i",
            "--name $proxyName",
            "-v \"$path\":/server/data",
            "-m $memory --memory-swap $memory",
            "-p $port:25577",
            "jonaznas/bungeecord:latest"
        )

        if (result.second.isNotEmpty()) {
            SystemRuntime.logger.error("Installation of $proxyName failed")

            val message = result.second.joinToString("\n")
            ErrorLogging().append(message)
            return false
        }

        SystemRuntime.logger.info("Installation of $proxyName completed")
        return true
    }

    fun remove(name: String): Boolean {
        val proxyName = "cloud-proxy-$name"

        SystemRuntime.logger.info("Deleting $proxyName")

        transaction {
            Proxy.deleteWhere { Proxy.name eq name }
        }

        DockerContainer().stop(proxyName)

        Thread.sleep(5000L)

        return DockerRemove().normal(proxyName)
    }

    fun setSubServers(proxy: String = "internal") {
        val config = ProxyConfigManager(proxy).get()
        val servers: MutableMap<String, ProxyConfigServerModel> = mutableMapOf()

        fun add(internalName: String, port: Int) {
            val server = ProxyConfigServerModel(
                motd = internalName,
                address = "host.docker.internal:$port",
                restricted = false
            )
            servers[internalName] = server
        }

        transaction {
            Static.selectAll().toList().forEach { t ->
                val port = t[Static.port]
                val simpleName = t[Static.name]
                val internalName = "cloud-static-$simpleName"
                add(internalName, port)
            }
        }

        config?.servers = servers

        ProxyConfigManager(proxy).overwrite(config)
    }

    fun addSubServer(name: String, port: Int) = SocketHandler().broadcast(
        "addServer",
        mapOf(
            "name" to name,
            "port" to port
        )
    )

    fun removeSubServer(name: String) = SocketHandler().broadcast(
        "removeServer",
        mapOf(
            "name" to name
        )
    )
}
