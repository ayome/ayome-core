package dev.jonaz.cloud.components.setup

import dev.jonaz.cloud.util.system.SystemRuntime
import dev.jonaz.cloud.util.system.os.SystemCheck
import dev.jonaz.cloud.util.system.os.SystemType
import java.io.File
import kotlin.system.exitProcess

class InstallationSetup {

    fun startInstallation() {
        SystemRuntime.logger.info("Setting up dependencies...")

        when (false) {
            DockerSetup().isInstalled() -> DockerSetup().install()
            DatabaseSetup().isExist() -> DatabaseSetup().finishSetup()
        }
    }
}
