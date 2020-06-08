package dev.jonaz.cloud.util.listener

import dev.jonaz.cloud.util.socket.SocketMappingInitializer
import dev.jonaz.cloud.util.socket.SocketServer
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ApplicationStartupListener : ApplicationListener<ApplicationReadyEvent> {
    override fun onApplicationEvent(event: ApplicationReadyEvent) {

        SocketServer()
            .startAsync()
        SocketMappingInitializer()
    }
}
