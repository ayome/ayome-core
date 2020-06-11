package dev.jonaz.cloud.shell.commands

import org.slf4j.LoggerFactory
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class ApplicationCommands {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ShellMethod("print a test")
    fun abc() {
        this.logger.info("hey was geht")
        
    }
}
