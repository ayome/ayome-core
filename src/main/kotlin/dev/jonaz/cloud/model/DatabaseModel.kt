package dev.jonaz.cloud.model

import org.jetbrains.exposed.sql.Table

open class DatabaseModel {

    companion object Application : Table("application") {
        val name = varchar("name", 256)
        val value = varchar("value", 256)
    }
}
