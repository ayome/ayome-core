package dev.jonaz.cloud.util.system.os

class SystemCheck {
    private val osName = System.getProperty("os.name", "generic").toLowerCase()

    fun getSystemTypes(): SystemType = when {
        osName.contains("win") -> SystemType.Windows
        osName.contains("nux") -> SystemType.Linux
        else -> SystemType.Other
    }
}
