package dev.jonaz.cloud.internal.addon

import dev.jonaz.cloud.internal.addon.command.AddServerCommand
import dev.jonaz.cloud.internal.addon.command.RemoveServerCommand
import net.md_5.bungee.api.plugin.Plugin

class Main : Plugin() {

    override fun onEnable() {
        proxy.pluginManager.registerCommand(this, AddServerCommand())
        proxy.pluginManager.registerCommand(this, RemoveServerCommand())

        proxy.logger.info("Cloud addon enabled")
    }
}
