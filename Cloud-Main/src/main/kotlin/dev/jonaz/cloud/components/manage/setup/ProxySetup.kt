package dev.jonaz.cloud.components.manage.setup

import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.filesystem.FileManager
import java.io.File

class ProxySetup(val name: String) {

    fun setupFiles() {
        useFile("config.yml")
        useModule("Cloud-Proxy.jar")
    }

    fun useModule(moduleName: String) {
        val modulePath = File(SystemPathManager.current + "internal/$moduleName")
        val destination = File(SystemPathManager.current + "proxy/$name/modules/$moduleName")

        destination.parentFile.mkdirs()
        FileManager().copyFile(modulePath.toPath(), destination.toPath())
    }

    private fun useFile(fileName: String) {
        val filePath = File(SystemPathManager.current + "internal/default/proxy/$fileName")
        val destination = File(SystemPathManager.current + "proxy/$name/$fileName")

        destination.parentFile.mkdirs()
        FileManager().copyFile(filePath.toPath(), destination.toPath())
    }
}
