package dev.jonaz.cloud.util.system

import java.io.File
import java.time.LocalDateTime

class ErrorLogging {
    private val file = File(SystemPathManager.current + "error.log")

    fun createFile() {
        try {
            file.createNewFile()
        } catch (e: Exception) {
            SystemRuntime.logger.error("Cannot create error.log file")
            e.printStackTrace()
        }
    }

    fun append(message: String) {
        val dateTime = LocalDateTime.now()
        file.appendText("\n$dateTime: $message\n")
    }
}
