package dev.jonaz.cloud.util.listener

import dev.jonaz.cloud.components.setup.InstallationSetup
import dev.jonaz.cloud.components.setup.NetworkSetup
import dev.jonaz.cloud.components.setup.SystemSetup
import dev.jonaz.cloud.util.exposed.DatabaseInitializer
import dev.jonaz.cloud.util.exposed.SchemaManager
import dev.jonaz.cloud.util.socket.SocketMappingInitializer
import dev.jonaz.cloud.util.socket.SocketServer
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component

@Component
class ApplicationStartupListener : InitializingBean {

    override fun afterPropertiesSet() {
        NetworkSetup().setupNetwork()

        SocketServer().startAsync()
        SocketMappingInitializer()

        DatabaseInitializer().connect()
        SchemaManager().createSchema()

        SystemSetup().isCompatible()
        InstallationSetup().startInstallation()
    }
}
