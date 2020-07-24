package dev.jonaz.cloud.util.system.filesystem

import dev.jonaz.cloud.util.system.SystemPathManager
import java.util.*

class PropertiesParser {

    fun fromCloudFile(path: String): Properties {
        val properties = Properties()
        val filePath = SystemPathManager.build(path)
        val resourceStream = this::class.java.classLoader.getResourceAsStream(filePath)

        properties.load(resourceStream)

        return properties
    }
}
