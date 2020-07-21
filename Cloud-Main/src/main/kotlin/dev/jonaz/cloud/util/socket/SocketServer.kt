package dev.jonaz.cloud.util.socket

import com.corundumstudio.socketio.SocketIOServer
import dev.jonaz.cloud.config.SocketConfiguration
import io.netty.util.concurrent.Future
import java.net.InetAddress
import java.net.ServerSocket

class SocketServer {
    companion object {
        lateinit var server: SocketIOServer
        lateinit var tcp: ServerSocket
    }

    init {
        val socketConfig = SocketConfiguration().get()
        server = SocketIOServer(socketConfig)

        tcp = ServerSocket(7403, 0, InetAddress.getByName(null))
        SocketHandler().acceptClients()
    }

    fun startAsync(): Future<Void> = server.startAsync()
}
