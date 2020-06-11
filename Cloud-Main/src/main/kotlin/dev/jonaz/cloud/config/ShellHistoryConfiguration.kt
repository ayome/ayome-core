package dev.jonaz.cloud.config

import org.jline.reader.impl.history.DefaultHistory
import org.springframework.stereotype.Component

@Component
class ShellHistoryConfiguration : DefaultHistory() {
    override fun save() {}
}
