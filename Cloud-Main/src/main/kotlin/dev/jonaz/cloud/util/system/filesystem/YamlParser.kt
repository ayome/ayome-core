package dev.jonaz.cloud.util.system.filesystem

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.jonaz.cloud.util.system.SystemPathManager
import java.io.File

class YamlParser {
    inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, object : TypeToken<T>() {}.type)

    inline fun <reified T> fromCloudFile(path: String): T? {
        val file = File(path)
        val mapper = ObjectMapper(YAMLFactory())

        if (file.exists().not()) return null

        mapper.findAndRegisterModules()

        val map = mapper.readValue(file, Map::class.java)
        val json = Gson().toJson(map)

        return Gson().fromJson(json)
    }
}
