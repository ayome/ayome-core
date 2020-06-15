package dev.jonaz.cloud.components.setup

import dev.jonaz.cloud.util.system.SystemRuntime
import dev.jonaz.cloud.util.system.os.SystemType
import kotlin.system.exitProcess

class SystemSetup {

    fun isCompatible() = when (SystemRuntime.systemType) {
        SystemType.Linux -> true
        SystemType.Windows -> {
            SystemRuntime.logger.warn("Windows detected. Please make sure you have docker desktop running and updated to WSL 2!")
            true
        }
        SystemType.Other -> {
            SystemRuntime.logger.error("Your operating system is not supported")
            exitProcess(0)
        }
    }.also {
        SystemRuntime.logger.info("Starting System. Please wait...")
    }
}
