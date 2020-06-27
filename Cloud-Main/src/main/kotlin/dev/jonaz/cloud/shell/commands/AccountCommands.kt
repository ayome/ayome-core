package dev.jonaz.cloud.shell.commands

import dev.jonaz.cloud.components.user.UserCreation
import dev.jonaz.cloud.model.DatabaseModel
import dev.jonaz.cloud.util.system.SystemRuntime
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class AccountCommands : DatabaseModel() {

    @ShellMethod("Will change the password to a random one")
    fun resetPassword(
        @ShellOption username: String
    ) {
        val newPassword = UserCreation().generatePassword()

        transaction {
            val user = User.select { User.username eq username }.toList()

            if (user.size != 1) {
                return@transaction false
            }

            User.update({ User.username eq username }) {
                it[password] = newPassword.first
            }

            SystemRuntime.logger.info(" ")
            SystemRuntime.logger.info("Password has been changed")
            SystemRuntime.logger.info("Username: $username")
            SystemRuntime.logger.info("Password: ${newPassword.second}")
            SystemRuntime.logger.info(" ")

            return@transaction true
        }
    }
}
