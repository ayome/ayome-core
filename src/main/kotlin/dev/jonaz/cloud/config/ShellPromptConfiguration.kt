package dev.jonaz.cloud.config

import org.jline.utils.AttributedString
import org.springframework.beans.factory.annotation.Value
import org.springframework.shell.jline.PromptProvider
import org.springframework.stereotype.Component

@Component
class ShellPromptConfiguration : PromptProvider {
    @Value("\${application.prefix}")
    private val prefix: String? = null

    override fun getPrompt() = AttributedString(this.prefix)
}
