package dev.jonaz.cloud.util.system

import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.time.LocalDateTime

class ErrorLogging {
    private val file = File(SystemPathManager.build("error.log"))

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

    fun writeStacktrace(e: Exception) {
        val stringWriter = StringWriter()
        e.printStackTrace(PrintWriter(stringWriter))
        append(stringWriter.toString())
    }
}
