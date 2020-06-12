package dev.jonaz.cloud.components.user

import dev.jonaz.cloud.model.DatabaseModel
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.security.SecureRandom
import kotlin.streams.asSequence

class UserCreation : DatabaseModel() {

    fun createNew(username: String) = when (isExist(username)) {
        true -> Pair(false, "")
        else -> {
            val password = generatePassword()
            transaction {
                User.insert {
                    it[User.username] = username
                    it[User.password] = password.first
                }
            }
            Pair(true, password.second)
        }
    }

    private fun isExist(username: String) = when (
        transaction {
            User.select { User.username eq username }.toList()
        }.size) {
        0 -> false
        else -> true
    }

    private fun generatePassword(): Pair<String, String> {
        val encoder = BCryptPasswordEncoder(10, SecureRandom())
        val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ.-,/*"
        val password = java.util.Random().ints(16, 0, source.length)
            .asSequence()
            .map(source::get)
            .joinToString("")
        val encrypted = encoder.encode(password)
        return Pair(encrypted, password)
    }
}
