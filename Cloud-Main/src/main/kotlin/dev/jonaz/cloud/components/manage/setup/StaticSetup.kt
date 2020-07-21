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

    private fun usePlugin(fileName: String) {
        val filePath = File(SystemPathManager.current + "internal/$fileName")
        val destination = File(SystemPathManager.current + "static/$name/plugins/$fileName")

        destination.parentFile.mkdirs()

        FileManager().copyFile(filePath.toPath(), destination.toPath())
    }

    private fun useFile(fileName: String) {
        val filePath = File(SystemPathManager.current + "internal/default/static/$fileName")
        val destination = File(SystemPathManager.current + "static/$name/$fileName")

        destination.parentFile.mkdirs()

        FileManager().copyFile(filePath.toPath(), destination.toPath())
    }
}
