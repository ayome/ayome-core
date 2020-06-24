package dev.jonaz.cloud.util.docker.container

import dev.jonaz.cloud.util.system.ErrorLogging
import dev.jonaz.cloud.util.system.SystemRuntime

class DockerRemove {

    fun normal(name: String): Boolean {
        val process = SystemRuntime.runtime.exec("docker rm $name")
        val errorStream = process.errorStream

        var errorLines = 0
        errorStream.bufferedReader().forEachLine { s ->
            errorLines++
            ErrorLogging().append(s)
            SystemRuntime.logger.error("Failed to delete $name. Open error.log for more information")
        }

        return errorLines == 0
    }
}
