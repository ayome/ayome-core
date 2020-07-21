package dev.jonaz.cloud.components.setup

import dev.jonaz.cloud.util.system.SystemRuntime
import dev.jonaz.cloud.util.system.filesystem.ResourcesManager
import dev.jonaz.cloud.util.update.internal.InternalUpdateConfigManager
import dev.jonaz.cloud.util.update.internal.InternalUpdateManager
import java.io.File

class InternalSetup {

    fun setupDefaultFiles() {
        SystemRuntime.logger.info("Updating internal files...")

        InternalUpdateConfigManager().validateConfig()
        InternalUpdateManager().checkUpdate()

        extractDefaultFile("/internal/proxy/config.yml", "internal/default/proxy/config.yml")
        extractDefaultFile("/internal/static/server.properties", "internal/default/static/server.properties")
        extractDefaultFile("/internal/static/spigot.yml", "internal/default/static/spigot.yml")
    }

    private fun extractDefaultFile(resourcePath: String, targetPath: String) {
        if (File(targetPath).exists().not()) {
            ResourcesManager().extractFileToPath(resourcePath, targetPath)
        }
    }
}
