package dev.jonaz.cloud.util.exposed

import org.jetbrains.exposed.sql.Database

class DatabaseInitializer {
    companion object {
        lateinit var dataSource: DataSourceModel
    }

    init {
        dataSource = DataSourceModel(
            "jdbc:sqlite:data.db",
            "org.sqlite.JDBC"
        )
    }

    fun connect() = Database.connect(dataSource.jdbcUrl, dataSource.driverClassName)
}
