package dev.jonaz.cloud.components.setup

import dev.jonaz.cloud.util.system.SystemRuntime
import java.lang.Exception
import java.net.Socket
import kotlin.system.exitProcess

class NetworkSetup {

    fun setupNetwork() = when (false) {
        isPortAvailable(7401) -> {
            SystemRuntime.logger.error("Port 7401 is already in use. Shutdown all applications running on port 7401 and 7402 and try it again")
            exitProcess(0)
        }
        isPortAvailable(7402) -> {
            SystemRuntime.logger.error("Port 7402 is already in use. Shutdown all applications running on port 7401 and 7402 and try it again")
            exitProcess(0)
        }
        else -> true
    }

    private fun isPortAvailable(port: Int): Boolean {
        return try {
            Socket("0.0.0.0", port).close()
            false
        } catch (e: Exception) {
            true
        }
    }
}
