package dev.jonaz.cloud.util.system

import org.springframework.core.env.Environment
import java.nio.file.Paths

class SystemPathManager {

    companion object {
        lateinit var current: String
    }

    fun setSystemPath(env: Environment) {
        val workDir = Paths.get("").toAbsolutePath().toString()
        current = when (env.activeProfiles[0]) {
            "dev", "development" -> "$workDir/build/libs/"
            else -> workDir
        }
    }
}
