package dev.jonaz.cloud.components.setup

import dev.jonaz.cloud.util.system.SystemRuntime

class InstallationSetup {

    fun startInstallation() {
        SystemRuntime.logger.info("Setting up dependencies...")

        when (false) {
            DockerSetup().isInstalled() -> DockerSetup().install()
            DatabaseSetup().isExist() -> DatabaseSetup().finishSetup()
        }
    }
}
