package dev.jonaz.cloud.util.docker.container

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import dev.jonaz.cloud.model.docker.DockerInspectModel
import dev.jonaz.cloud.util.system.SystemRuntime
import java.lang.Exception

class DockerInspect {

    fun getByName(name: String): Pair<Boolean, Map<*, *>?> {
        val inspection = SystemRuntime().exec("docker inspect $name")
        return try {
            var objectString = ""

            inspection.forEach { t -> objectString += t }
            val objects = Gson().fromJson(objectString, List::class.java)
            if (objects.isNotEmpty()) {
                val singleObject = Gson().toJson(objects.get(0))
                val mappedObject = Gson().fromJson(singleObject, DockerInspectModel::class.java)
            }

            Pair(true, null)
        } catch (e: Exception) {
            e.printStackTrace()
            Pair(false, null)
        }
    }
}
