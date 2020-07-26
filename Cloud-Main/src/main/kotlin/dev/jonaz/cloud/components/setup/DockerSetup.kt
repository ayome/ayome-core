package dev.jonaz.cloud.components.setup

import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.SystemRuntime
import dev.jonaz.cloud.util.system.os.SystemType
import java.io.File
import java.net.URL
import kotlin.system.exitProcess

class DockerSetup {

    fun isInstalled(): Boolean {
        val info = SystemRuntime().exec("docker top .")

        return when {
            info.second.size != 1 -> false
            info.second.get(0).contains("Error response from daemon: page not found") -> true
            else -> false
        }
    }

    fun install() = when (SystemRuntime.systemType) {
        SystemType.Windows -> {
            val version = SystemRuntime().exec("docker --version")

            if (version.first.get(0).contains("Docker version")) {
                SystemRuntime.logger.error("Docker engine is not running")
            } else {
                SystemRuntime.logger.error("On windows you need to install docker manually")
            }

            SystemRuntime.logger.error("More information here https://docs.docker.com/docker-for-windows/install/")
            SystemRuntime.logger.error("Make sure docker engine is updated to WSL2")
            exitProcess(0)
        }
        else -> {
            SystemRuntime.logger.info("Installing docker...")
            linuxInstall()

            if (!isInstalled()) {
                SystemRuntime.logger.error("Failed to install docker")
                SystemRuntime.logger.error("Install docker manually on your system and try it again")
                exitProcess(0)
            } else true
        }
    }

    private fun linuxInstall() {
        val script = URL("https://get.docker.com").readText()
        val file = File(SystemPathManager.build("get-docker.sh"))

        file.createNewFile()
        file.setExecutable(true)

        file.writeText(script)

        val process = SystemRuntime.runtime.exec(SystemPathManager.build("get-docker.sh"))
        process.waitFor()

        file.delete()
    }
}
