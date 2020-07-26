package dev.jonaz.cloud.util.system

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.jonaz.cloud.model.config.ApplicationConfigModel
import java.io.File

class ApplicationConfigManager {
    private val path = SystemPathManager.build("config.json")
    private val file = File(path)

    companion object {
        lateinit var config: ApplicationConfigModel
    }

    fun setup() = when (false) {
        exist() -> create()
        validate() -> create()
        else -> config = get()
    }

    private fun create() {
        val address = NetworkUtils().getPublicAddress()
        val model = ApplicationConfigModel(
            "http:/$address:7401"
        )
        val builder = GsonBuilder().setPrettyPrinting().create()
        val json = builder.toJson(model)

        file.delete()
        file.createNewFile()
        file.writeText(json)
        config = get()
    }

    private fun get() = Gson().fromJson(file.readText(), ApplicationConfigModel::class.java)

    private fun exist() = file.exists()

    private fun validate() = try {
        Gson().fromJson(file.readText(), ApplicationConfigModel::class.java)
        true
    } catch (e: Exception) {
        false
    }
}
