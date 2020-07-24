package dev.jonaz.cloud.components.manage.setup

import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.filesystem.FileManager
import java.io.File

class StaticSetup(val name: String) {

    fun setupFiles() {
        useFile("server.properties")
        useFile("spigot.yml")
        usePlugin("Cloud-Static.jar")
    }

    fun usePlugin(fileName: String) {
        val filePath = File(SystemPathManager.build("internal", fileName))
        val destination = File(SystemPathManager.build("static", name, "plugins", fileName))

        destination.parentFile.mkdirs()

        FileManager().copyFile(filePath.toPath(), destination.toPath())
    }

    private fun useFile(fileName: String) {
        val filePath = File(SystemPathManager.build("internal", "default", "static", fileName))
        val destination = File(SystemPathManager.build("static", name, fileName))

        destination.parentFile.mkdirs()

        FileManager().copyFile(filePath.toPath(), destination.toPath())
    }
}
