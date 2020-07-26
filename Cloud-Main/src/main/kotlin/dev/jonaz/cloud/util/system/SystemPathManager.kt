package dev.jonaz.cloud.util.system

import java.nio.file.Paths

class SystemPathManager {
    companion object {
        lateinit var current: String

        fun build(vararg name: String): String {
            return Paths.get(current, *name).toAbsolutePath().toString()
        }
    }

    fun setSystemPath() {
        val workDir = Paths.get("").toAbsolutePath().toString()

        current = if (EnvironmentUtils.development)
            Paths.get(workDir, "build", "libs").toAbsolutePath().toString()
        else
            Paths.get(workDir).toAbsolutePath().toString()
    }
}
