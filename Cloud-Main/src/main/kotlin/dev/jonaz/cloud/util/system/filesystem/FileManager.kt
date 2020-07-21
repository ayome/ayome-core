package dev.jonaz.cloud.util.system.filesystem

import dev.jonaz.cloud.util.system.ErrorLogging
import dev.jonaz.cloud.util.system.SystemRuntime
import java.io.FileNotFoundException
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class FileManager {

    fun copyFile(file: Path, destination: Path) {
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
