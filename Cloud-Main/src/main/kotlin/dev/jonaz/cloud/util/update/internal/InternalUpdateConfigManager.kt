package dev.jonaz.cloud.util.update.internal

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.jonaz.cloud.model.config.internal.InternalUpdateConfigModel
import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.filesystem.ResourcesManager
import java.io.File
import java.io.FileNotFoundException

class InternalUpdateConfigManager {
    private val path = SystemPathManager.build("internal", "internal.json")

    fun getConfig(): InternalUpdateConfigModel {
        validateConfig()

        val file = File(path)
        return Gson().fromJson(file.readText(), InternalUpdateConfigModel::class.java)
    }


    fun validateConfig() {
        val file = File(path)

        try {
            Gson().fromJson(file.readText(), InternalUpdateConfigModel::class.java)
        } catch (e: FileNotFoundException) {
            createConfig()
        } catch (e: Exception) {
            file.delete()
            createConfig()
        }
    }

    fun updateConfig(newConfig: InternalUpdateConfigModel) {
        File(path).delete()

        val file = File(path)
        val builder = GsonBuilder().setPrettyPrinting().create()
        val json = builder.toJson(newConfig)

        file.createNewFile()
        file.writeText(json)
    }

    private fun createConfig() {
        ResourcesManager().extractFileToPath("/internal/internal.json", "internal/internal.json")
    }
}
