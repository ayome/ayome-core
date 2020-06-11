package dev.jonaz.cloud.components.setup

import dev.jonaz.cloud.util.system.SystemRuntime
import dev.jonaz.cloud.util.system.os.SystemCheck
import dev.jonaz.cloud.util.system.os.SystemType
import java.io.File
import kotlin.system.exitProcess

class InstallationSetup {

    companion object {
        val systemType = SystemCheck().getSystemTypes()
    }

    fun isInstalled(): Boolean = when (systemType) {
        SystemType.Windows, SystemType.Linux -> {
            SystemRuntime.logger.info("Checking setup")

            when (false) {
                DockerSetup().isInstalled() -> false
                DatabaseSetup().isExist() -> false
                else -> true
            }
        }

        SystemType.Other -> {
            SystemRuntime.logger.error("Your operating system is not supported")
            exitProcess(0)
        }
    }

    fun startInstallation() {
        SystemRuntime.logger.info("Setting up dependencies")

        DockerSetup().install()
    }
}
