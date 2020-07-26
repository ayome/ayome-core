package dev.jonaz.cloud.config

import com.corundumstudio.socketio.AckMode
import com.corundumstudio.socketio.Configuration
import dev.jonaz.cloud.components.setup.SystemSetup
import dev.jonaz.cloud.util.system.ApplicationConfigManager
import dev.jonaz.cloud.util.system.EnvironmentUtils

class SocketConfiguration {
    private val config = Configuration()

    init {
        config.ackMode = AckMode.MANUAL
        config.workerThreads = 8
        config.bossThreads = 4
        config.port = 7402

        if (EnvironmentUtils.development.not())
            config.origin = ApplicationConfigManager.config.uiBind
    }

    fun get(): Configuration = config
}
