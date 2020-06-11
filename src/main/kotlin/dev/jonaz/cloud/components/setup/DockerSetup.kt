package dev.jonaz.cloud.components.setup

import dev.jonaz.cloud.util.system.SystemRuntime
import dev.jonaz.cloud.util.system.os.SystemType
import kotlin.system.exitProcess

class DockerSetup {

    fun isInstalled(): Boolean = when (SystemRuntime().exec("docker info")[0]) {
        "Client:" -> true
        else -> false
    }

    fun install() = when (SystemRuntime.systemType) {
        SystemType.Windows -> {
            SystemRuntime.logger.error("On windows you need to install docker manually")
            SystemRuntime.logger.error("More information here https://docs.docker.com/docker-for-windows/install/")
            exitProcess(0)
        }
        else -> {
            SystemRuntime.logger.info("Installing docker...")
            SystemRuntime().exec("curl -L https://get.docker.com | bash")

            if (!isInstalled()) {
                SystemRuntime.logger.error("Failed to install docker")
                SystemRuntime.logger.error("Install docker manually on your system and try it again")
                exitProcess(0)
            } else true
        }
    }
}
