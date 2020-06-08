package dev.jonaz.cloud.util.socket

import com.corundumstudio.socketio.SocketIOServer
import dev.jonaz.cloud.config.SocketConfiguration
import io.netty.util.concurrent.Future

class SocketServer {
    companion object {
        lateinit var server: SocketIOServer
    }

    init {
        val socketConfig = SocketConfiguration().get()
        server = SocketIOServer(socketConfig)
    }

    fun startAsync(): Future<Void> = server.startAsync()
}
