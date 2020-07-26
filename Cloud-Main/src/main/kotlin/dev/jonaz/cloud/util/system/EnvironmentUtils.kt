package dev.jonaz.cloud.util.system

import org.springframework.core.env.Environment

class EnvironmentUtils {
    companion object {
        lateinit var current: Environment
        var development = true
    }

    fun setEnvironment(env: Environment) {
        current = env
        development = when (env.activeProfiles[0]) {
            "dev", "development" -> true
            else -> false
        }
    }
}
