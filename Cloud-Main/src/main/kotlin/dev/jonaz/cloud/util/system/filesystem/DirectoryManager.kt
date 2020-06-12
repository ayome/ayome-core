package dev.jonaz.cloud.util.system.filesystem

import dev.jonaz.cloud.util.system.SystemRuntime
import java.io.File

class DirectoryManager {

    fun create(path: String): Boolean {
        return try {
            File(path).mkdirs()
            true
        } catch (e: AccessDeniedException) {
            SystemRuntime.logger.error("Cannot create directories (access denied)")
            false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
