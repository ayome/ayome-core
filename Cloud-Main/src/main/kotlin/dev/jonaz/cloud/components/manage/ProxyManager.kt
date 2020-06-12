package dev.jonaz.cloud.components.manage

import dev.jonaz.cloud.model.DatabaseModel
import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.SystemRuntime
import dev.jonaz.cloud.util.system.filesystem.DirectoryManager
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class ProxyManager : DatabaseModel() {

    fun getProxy(name: String): Pair<Boolean, Map<*, *>?> {
        val proxies = transaction {
            Proxy.select { Proxy.name eq name }.toList()
        }

        if (proxies.isEmpty()) {
            return Pair(false, null)
        }

        return Pair(true, mapOf("test" to "abc"))
    }

    fun installProxy(name: String, memory: Int, port: Int): Boolean {
        val path = "${SystemPathManager.current}proxy/$name"
        SystemRuntime.logger.info("Starting installation of proxy named $name")

        DirectoryManager().create(path)

        transaction {
            Proxy.insert {
                it[Proxy.name] = name
                it[Proxy.memory] = memory
                it[Proxy.port] = port
            }
        }

        SystemRuntime().exec("docker run -d --name cloud-proxy-$name -v $path:/server -p $port:25577 itzg/bungeecord")
        SystemRuntime.logger.info("Installation of proxy $name completed")
        return true
    }
}
