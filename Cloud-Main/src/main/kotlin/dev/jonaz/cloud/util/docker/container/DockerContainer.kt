package dev.jonaz.cloud.util.docker.container

import dev.jonaz.cloud.util.system.SystemRuntime

class DockerContainer {

    fun start(name: String): Boolean {
        SystemRuntime.logger.info("Starting $name")

        val started = SystemRuntime().exec("docker start $name").first.toTypedArray()
        val success = started.isNotEmpty() && started.get(0).equals(name)

        if (success) {
            SystemRuntime.logger.info("Successfully started $name")
            DockerStats().startStreamToChannel(name)
        }
        else SystemRuntime.logger.error("Failed to start $name")

        return success
    }

    fun stop(name: String): Boolean {
        SystemRuntime.logger.info("Stopping $name")

        val stopped = SystemRuntime().exec("docker stop $name").first.toTypedArray()
        val success = stopped.isNotEmpty() && stopped.get(0).equals(name)

        if (success) SystemRuntime.logger.info("Successfully stopped $name")
        else SystemRuntime.logger.error("Failed to stop $name")

        return success
    }

    fun delete(name: String): Boolean {
        val deleted = SystemRuntime().exec("docker rm -f $name").first.toTypedArray()

        return deleted.isNotEmpty() && deleted.get(0).equals(name)
    }
}
