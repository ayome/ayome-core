package dev.jonaz.cloud.util.docker.container

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import dev.jonaz.cloud.util.system.SystemRuntime
import java.lang.Exception

class DockerContainerFind {

    fun getByName(name: String): Pair<Boolean, JsonNode?> {
        val inspection = SystemRuntime().exec("docker inspect $name")
        return try {
            var objectString = ""

            inspection.forEach { t -> objectString += t }
            val objectMap = ObjectMapper().readTree(objectString)

            Pair(true, objectMap)
        } catch (e: Exception) {
            e.printStackTrace()
            Pair(false, null)
        }
    }
}
