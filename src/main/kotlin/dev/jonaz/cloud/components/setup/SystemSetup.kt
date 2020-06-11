package dev.jonaz.cloud.components.setup

import dev.jonaz.cloud.util.system.SystemRuntime
import dev.jonaz.cloud.util.system.os.SystemType
import kotlin.system.exitProcess

class SystemSetup {

    fun isCompatible() = when(SystemRuntime.systemType) {
        SystemType.Windows, SystemType.Linux -> true
        SystemType.Other -> {
            SystemRuntime.logger.error("Your operating system is not supported")
            exitProcess(0)
        }
    }
}
