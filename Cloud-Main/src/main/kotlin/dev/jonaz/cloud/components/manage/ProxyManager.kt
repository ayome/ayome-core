package dev.jonaz.cloud.components.manage

import dev.jonaz.cloud.model.DatabaseModel
import dev.jonaz.cloud.model.docker.DockerInspectModel
import dev.jonaz.cloud.util.docker.container.DockerContainer
import dev.jonaz.cloud.util.docker.container.DockerInspect
import dev.jonaz.cloud.util.docker.container.DockerStats
import dev.jonaz.cloud.util.system.ErrorLogging
import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.SystemRuntime
import dev.jonaz.cloud.util.system.filesystem.DirectoryManager
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
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

    fun installProxy(name: String, memory: Int, port: Int): Boolean {
        SystemRuntime.logger.info("Starting installation of proxy named $name")

        val proxyName = "cloud-proxy-$name"
        val path = "${SystemPathManager.current}proxy/$name"
        DirectoryManager().create(path)

        transaction {
            Proxy.deleteWhere { Proxy.name eq name }
            Proxy.insert {
                it[Proxy.name] = name
                it[Proxy.memory] = memory
                it[Proxy.port] = port
            }
        }

        DockerContainer().delete(proxyName)
        val result = SystemRuntime().exec("docker run -d --name $proxyName -v \"$path\":/server -p $port:25577 itzg/bungeecord")

        if(result.second.isNotEmpty()) {
            SystemRuntime.logger.error("Installation of proxy $proxyName failed")
            ErrorLogging().append(result.second.joinToString("\n"))
            return false
        }

        SystemRuntime.logger.info("Installation of proxy $proxyName completed")
        return true
    }
}