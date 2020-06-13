package dev.jonaz.cloud.components.manage

import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.SystemRuntime
import dev.jonaz.cloud.util.system.filesystem.DirectoryManager

class InstallationManager {

    fun installProxy(name: String, memory: Int, port: Int): Pair<Boolean, String> {
        val path = "${SystemPathManager.current}/proxy/$name"
        val success = DirectoryManager().create(path)

        return if (success) {
            SystemRuntime().exec("docker run -v $path:/server --net=\"host\" -p $port:25577 itzg/bungeecord")
            Pair(true, "")
        } else {
            Pair(false, "Failed to create proxy. Check the console for more information.")
        }
    }
}
