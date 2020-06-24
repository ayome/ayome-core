package dev.jonaz.cloud.util.docker.container

import dev.jonaz.cloud.util.system.ErrorLogging
import dev.jonaz.cloud.util.system.SystemRuntime

class DockerUpdate {

    fun updateMemory(container: String, memory: Long, swap: Long): Boolean {
        val result = SystemRuntime().exec("docker update -m $memory --memory-swap $swap $container")
        if (result.second.isNotEmpty()) {
            SystemRuntime.logger.error("Failed to update memory. Open error.log to get more information")
            ErrorLogging().append(result.second.joinToString())
        }
        return result.second.isEmpty()
    }
}
