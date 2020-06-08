package dev.jonaz.cloud.config

import com.corundumstudio.socketio.AckMode
import com.corundumstudio.socketio.Configuration

class SocketConfiguration {
    private val config = Configuration()

    init {
        config.ackMode = AckMode.MANUAL
        config.workerThreads = 8
        config.bossThreads = 4
        config.hostname = "127.0.0.1"
        config.port = 7402
    }

    fun get(): Configuration = config
}
