package dev.jonaz.cloud.util.listener

import dev.jonaz.cloud.util.exposed.DatabaseInitializer
import dev.jonaz.cloud.util.exposed.SchemaManager
import dev.jonaz.cloud.util.socket.SocketMappingInitializer
import dev.jonaz.cloud.util.socket.SocketServer
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ApplicationStartupListener : ApplicationListener<ApplicationStartedEvent> {
    override fun onApplicationEvent(event: ApplicationStartedEvent) {

        SocketServer().startAsync()
        SocketMappingInitializer()

        DatabaseInitializer().connect()
        SchemaManager().createSchema()
    }
}
