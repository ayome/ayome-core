package dev.jonaz.cloud.components.manage.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import dev.jonaz.cloud.model.config.proxy.ProxyConfigModel
import dev.jonaz.cloud.util.system.SystemPathManager
import dev.jonaz.cloud.util.system.filesystem.YamlParser
import java.io.File

class ProxyConfigManager(name: String) {
    private val path = SystemPathManager.build("proxy", name, "config.yml")

    fun get(): ProxyConfigModel? {
        return YamlParser().fromCloudFile<ProxyConfigModel>(path)
    }

    fun overwrite(config: ProxyConfigModel?) {
        val file = File(path)

        if (file.exists().not()) return

        val mapper = ObjectMapper(
            YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
        )

        mapper.writeValue(file, config)
    }
}
