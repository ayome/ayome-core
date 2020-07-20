package dev.jonaz.cloud.util.system.filesystem

import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.SystemRuntime
import java.io.File

class ResourcesManager {

    fun extractFileToPath(resourcePath: String, targetPath: String) {
        val content = this::class.java.getResource(resourcePath).readText()
        val target = File(SystemPathManager.current + targetPath)

        try {
            target.parentFile.mkdirs()
            target.createNewFile()
            target.appendText(content)
        } catch (e: FileAlreadyExistsException) {
            return
        } catch (e: Exception) {
            SystemRuntime.logger.error("Cannot extract resources file")
            e.printStackTrace()
        }
    }
}
