package dev.jonaz.cloud.config

import org.jline.utils.AttributedString
import org.springframework.shell.jline.PromptProvider
import org.springframework.stereotype.Component

@Component
class ShellPromptConfiguration : PromptProvider {

    override fun getPrompt() = AttributedString("")
}
