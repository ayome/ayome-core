package dev.jonaz.cloud.components.setup

import dev.jonaz.cloud.util.system.SystemRuntime

class InstallationSetup {

    fun startInstallation() {
        SystemRuntime.logger.info("Setting up dependencies...")

        if (DockerSetup().isInstalled().not())
            DockerSetup().install()

        if (DatabaseSetup().isExist())
            DatabaseSetup().finishSetup()
    }
}
