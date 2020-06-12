package dev.jonaz.cloud.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

open class DatabaseModel {

    object Application : Table("application") {
        val name = varchar("name", 256)
        val value = varchar("value", 256)
    }

    object Users : Table("users") {
        val username = text("username")
        val password = text("password")
        val createdAt = datetime("createdAt").default(LocalDateTime.now())

        override val primaryKey = PrimaryKey(username, name = "username")
    }
}
