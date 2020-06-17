package dev.jonaz.cloud.util.system

import dev.jonaz.cloud.util.system.os.SystemCheck
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.InputStream
import java.util.concurrent.TimeUnit

class SystemRuntime {
    companion object {
        val systemType = SystemCheck().getSystemTypes()
        val runtime: Runtime = Runtime.getRuntime()
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * @param command String
     * @param timeout Long (Seconds)
     * @return List<String>
     */
    fun exec(command: String, timeout: Long = 0): Pair<List<String>, List<String>> {
        val result = mutableListOf<String>()
        val errors = mutableListOf<String>()
        try {
            val process = runtime.exec(command)

            if (timeout != 0L) process.waitFor(timeout, TimeUnit.SECONDS)

            val inputReader = process.inputStream.bufferedReader()
            val errorReader = process.errorStream.bufferedReader()

            inputReader.forEachLine { s ->
                result.add(s)
            }

            errorReader.forEachLine { s ->
                errors.add(s)
            }

            inputReader.close()
            errorReader.close()
        } catch (e: Exception) {
            logger.error("Failed to exec command on your system (${e.message})")
        }
        return Pair(result, errors)
    }

    fun execRaw(command: String): Process {
        return runtime.exec(command)
    }
}
