package dev.jonaz.cloud.components.manage.setup

import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.SystemRuntime
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class ProxySetup(val name: String) {

    fun setupFiles() {
        useDefaultFile("config.yml")
        useDefaultModule("cloud_addon")
    }

    fun useDefaultModule(moduleName: String) {
        val modulesPath = SystemPathManager.current + "proxy/$name/modules/"
        val module = this::class.java.getResourceAsStream("/default/proxy/modules/$moduleName.jar")
        val destination = Paths.get("$modulesPath$moduleName.jar")

        try {
            File(modulesPath).mkdirs()
            Files.copy(module, destination, StandardCopyOption.REPLACE_EXISTING)
        } catch (e: Exception) {
            SystemRuntime.logger.error("Cannot load default proxy module $moduleName")
        }
    }

    private fun useDefaultFile(fileName: String) {
        val content = this::class.java.getResource("/default/proxy/$fileName").readText()
        val file = File(SystemPathManager.current + "proxy/$name/$fileName")

        try {
            file.createNewFile()
            file.appendText(content)
        } catch (e: FileAlreadyExistsException) {
            return
        } catch (e: Exception) {
            SystemRuntime.logger.error("Cannot load default proxy file $fileName")
        }
    }
}
