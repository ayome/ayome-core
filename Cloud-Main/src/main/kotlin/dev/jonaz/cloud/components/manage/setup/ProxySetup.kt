package dev.jonaz.cloud.components.manage.setup

import dev.jonaz.cloud.util.system.ErrorLogging
import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.SystemRuntime
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class ProxySetup(val name: String) {

    fun setupFiles() {
        useFile("config.yml")
        useModule("Cloud-Proxy.jar")
    }

    fun useModule(moduleName: String) {
        val modulePath = File(SystemPathManager.current + "internal/$moduleName")
        val destination = File(SystemPathManager.current + "proxy/$name/modules/$moduleName")

        destination.parentFile.mkdirs()
        copyFile(modulePath.toPath(), destination.toPath())
    }

    private fun useFile(fileName: String) {
        val filePath = File(SystemPathManager.current + "internal/default/$fileName")
        val destination = File(SystemPathManager.current + "proxy/$name/$fileName")

        destination.parentFile.mkdirs()
        copyFile(filePath.toPath(), destination.toPath())
    }

    private fun copyFile(file: Path, destination: Path) {
        try {
            Files.copy(file, destination, StandardCopyOption.REPLACE_EXISTING)
        } catch (_: FileNotFoundException) {
            SystemRuntime.logger.error("Cannot find $file")
        } catch (e: AccessDeniedException) {
            SystemRuntime.logger.error("Cannot access $file")
        } catch (e: Exception) {
            SystemRuntime.logger.error("Failed to use $file. Open error.log for more information")
            ErrorLogging().writeStacktrace(e)
        }
    }
}
