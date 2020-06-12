package dev.jonaz.cloud.util.system

import org.springframework.core.env.Environment

class SystemPathManager {

    companion object {
        lateinit var current: String
    }

    fun setSystemPath(env: Environment) {
        current = when (env.activeProfiles[0]) {
            "dev", "development" -> "build/libs"
            else -> ""
        }
    }
}
