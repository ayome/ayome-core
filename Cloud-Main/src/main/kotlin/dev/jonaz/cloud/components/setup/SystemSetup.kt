package dev.jonaz.cloud.components.setup

import dev.jonaz.cloud.util.system.SystemRuntime
import dev.jonaz.cloud.util.system.os.SystemType
import org.springframework.core.env.Environment
import kotlin.system.exitProcess

class SystemSetup {
    fun isCompatible() = when (SystemRuntime.systemType) {
        SystemType.Linux -> true
        SystemType.Windows -> true
        SystemType.Other -> {
            SystemRuntime.logger.error("Your operating system is not supported")
            exitProcess(0)
        }
    }.also {
        SystemRuntime.logger.info("Starting System. Please wait...")
    }
}
