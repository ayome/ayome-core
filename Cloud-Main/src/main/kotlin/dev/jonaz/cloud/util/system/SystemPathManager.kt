package dev.jonaz.cloud.util.system

import org.springframework.core.env.Environment
import java.nio.file.Paths

class SystemPathManager {

    companion object {
        lateinit var current: String

        fun build(vararg name: String): String {
            return Paths.get(current, *name).toAbsolutePath().toString()
        }
    }

    fun setSystemPath(env: Environment) {
        val workDir = Paths.get("").toAbsolutePath().toString()
        current = when (env.activeProfiles[0]) {
            "dev", "development" -> Paths.get(workDir, "build", "libs").toAbsolutePath().toString()
            else -> Paths.get(workDir).toAbsolutePath().toString()
        }
    }
}
