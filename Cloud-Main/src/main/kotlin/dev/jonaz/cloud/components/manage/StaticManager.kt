package dev.jonaz.cloud.components.manage

import dev.jonaz.cloud.components.manage.setup.StaticSetup
import dev.jonaz.cloud.model.DatabaseModel
import dev.jonaz.cloud.model.docker.DockerInspectModel
import dev.jonaz.cloud.model.server.StaticServerModel
import dev.jonaz.cloud.util.docker.container.DockerContainer
import dev.jonaz.cloud.util.docker.container.DockerInspect
import dev.jonaz.cloud.util.docker.container.DockerRemove
import dev.jonaz.cloud.util.docker.system.DockerExec
import dev.jonaz.cloud.util.system.ErrorLogging
import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.SystemRuntime
import dev.jonaz.cloud.util.system.filesystem.DirectoryManager
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
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

    fun getStatic(name: String): Pair<Boolean, DockerInspectModel?> {
        val staticName = "cloud-static-$name"

        val statics = transaction {
            Static.select { Static.name eq name }.toList()
        }

        if (statics.isEmpty()) {
            return Pair(false, null)
        }

        val result = DockerInspect().getByName(staticName)

        return Pair(result.first, result.second)
    }

    fun exec(container: String, command: String, timeout: Int = 0) {
        DockerExec().pty(container, "echo '$command' > /proc/1/fd/0", timeout)
    }

    fun remove(name: String): Boolean {
        val staticName = "cloud-static-$name"

        SystemRuntime.logger.info("Deleting $staticName")

        transaction {
            Static.deleteWhere { Static.name eq name }
        }

        ProxyManager().removeSubServer(staticName)
        DockerContainer().stop(staticName)

        Thread.sleep(5000L)

        return DockerRemove().normal(staticName)
    }
}
