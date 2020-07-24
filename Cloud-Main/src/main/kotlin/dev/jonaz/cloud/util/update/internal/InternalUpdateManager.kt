package dev.jonaz.cloud.util.update.internal

import com.google.gson.Gson
import dev.jonaz.cloud.model.config.internal.InternalUpdateSourceModel
import dev.jonaz.cloud.util.system.ErrorLogging
import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.SystemRuntime
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels

class InternalUpdateManager {
    val config = InternalUpdateConfigManager().getConfig()

    fun checkUpdate() {
        if (!config.update) return
        val newConfig = config

        config.sources.forEachIndexed { i, t ->
            try {
                val release = URL("https://api.github.com/repos/${t.release}/releases/latest").readText()
                val data = Gson().fromJson(release, Map::class.java)
                val file = File(SystemPathManager.build("internal", t.name))

                val tag = data["tag_name"] as String

                if (tag != t.tag || file.exists().not()) {
                    doUpdate(t, tag)
                    newConfig.sources[i].tag = tag
                }
            } catch (e: Exception) {
                SystemRuntime.logger.error("Failed to update an internal file. Open error.log for more information.")
                ErrorLogging().writeStacktrace(e)
                return@forEachIndexed
            }
        }

        InternalUpdateConfigManager().updateConfig(newConfig)
    }

    private fun doUpdate(t: InternalUpdateSourceModel, newTag: String) {
        val filePath = SystemPathManager.build("internal", t.name)

        File(filePath).delete()

        val release = URL("https://github.com/${t.release}/releases/download/${newTag}/${t.name}")
        val channel = Channels.newChannel(release.openStream())
        val stream = FileOutputStream(filePath)
        stream.channel.transferFrom(channel, 0, Long.MAX_VALUE)
    }
}
