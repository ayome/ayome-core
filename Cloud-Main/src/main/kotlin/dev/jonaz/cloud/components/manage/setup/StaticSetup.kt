package dev.jonaz.cloud.components.manage.setup

import dev.jonaz.cloud.util.system.ErrorLogging
import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.SystemRuntime
import java.io.File
import java.lang.Exception

class StaticSetup(val name: String) {

    fun setupFiles() {
        useDefaultFile("server.properties")
        useDefaultFile("spigot.yml")
    }

    private fun useDefaultFile(fileName: String) {
        val content = this::class.java.getResource("/default/static/$fileName").readText()
        val file = File(SystemPathManager.current + "static/$name/$fileName")

        try {
            file.createNewFile()
            file.appendText(content)
        } catch (e: FileAlreadyExistsException) {
            return
        } catch (e: Exception) {
            SystemRuntime.logger.error("Cannot load default $fileName")
            ErrorLogging().append(e.stackTrace.toString())
        }
    }
}
