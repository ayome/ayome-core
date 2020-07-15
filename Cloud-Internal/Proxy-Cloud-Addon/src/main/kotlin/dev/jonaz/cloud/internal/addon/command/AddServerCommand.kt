package dev.jonaz.cloud.internal.addon.command

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Command
import java.net.InetSocketAddress

class AddServerCommand : Command("cloudCommandAddServer", "cloud.*") {

    override fun execute(sender: CommandSender?, args: Array<out String>?) {
        if (sender?.name.equals("CONSOLE").not()) return

        val serverName: String
        val serverPort: Int

        when {
            args == null -> return
            args.size != 2 -> return
            else -> {
                serverName = args.get(0)
                serverPort = args.get(1).toInt()
            }
        }

        ProxyServer.getInstance().servers[serverName] = ProxyServer.getInstance().constructServerInfo(
            serverName,
            InetSocketAddress("host.docker.internal", serverPort),
            serverName,
            false
        )
    }
}
