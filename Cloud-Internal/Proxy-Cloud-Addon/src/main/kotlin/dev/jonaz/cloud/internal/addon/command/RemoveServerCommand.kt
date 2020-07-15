package dev.jonaz.cloud.internal.addon.command

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Command

class RemoveServerCommand : Command("cloudCommandRemoveServer", "cloud.*") {

    override fun execute(sender: CommandSender?, args: Array<out String>?) {
        if (sender?.name.equals("CONSOLE").not()) return

        val serverName: String

        when {
            args == null -> return
            args.size != 1 -> return
            else -> {
                serverName = args.get(0)
            }
        }

        ProxyServer.getInstance().servers.remove(serverName)
    }
}
