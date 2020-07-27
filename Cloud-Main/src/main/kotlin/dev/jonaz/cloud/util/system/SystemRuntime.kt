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
    fun exec(vararg command: String, timeout: Long = 0): Pair<List<String>, List<String>> {
        val result = mutableListOf<String>()
        val errors = mutableListOf<String>()
        try {
            val builder = ProcessBuilder(*command)
            val process = builder.start()

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
            logger.debug("Failed to exec")
            ErrorLogging().writeStacktrace(e)
        }
        return Pair(result, errors)
    }

    fun execRaw(vararg command: String): Process {
        return ProcessBuilder(*command).start()
    }
}
