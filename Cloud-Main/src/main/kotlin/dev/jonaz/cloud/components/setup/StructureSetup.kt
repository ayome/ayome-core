package dev.jonaz.cloud.components.setup

import com.fasterxml.jackson.databind.ObjectMapper
import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.filesystem.DirectoryManager
import kotlin.system.exitProcess

class StructureSetup {
    private val structure = this::class.java.classLoader.getResource("structure.json")?.readText()

    fun createDirectories() {
        val array = ObjectMapper().readTree(structure)
        array.forEach { t ->
            DirectoryManager().create(SystemPathManager.current + t.asText()).also { b ->
                if (!b) exitProcess(0)
            }
        }
    }
}
