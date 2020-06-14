package dev.jonaz.cloud.util.system

import dev.jonaz.cloud.util.system.os.SystemCheck
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.InputStream

class SystemRuntime {
    companion object {
        val systemType = SystemCheck().getSystemTypes()
        val runtime: Runtime = Runtime.getRuntime()
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    fun exec(command: String): List<String> {
        val result = mutableListOf<String>()
        try {
            val process = runtime.exec(command)

            val reader = process.inputStream.bufferedReader()

            reader.forEachLine { s ->
                result.add(s)
            }

            reader.close()
        } catch (e: Exception) {
            logger.error("Failed to exec command on your system (${e.message})")
        }
        return result
    }

    fun execRaw(command: String): Process {
        return runtime.exec(command)
    }
}
