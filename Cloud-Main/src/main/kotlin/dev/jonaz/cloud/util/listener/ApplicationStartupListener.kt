package dev.jonaz.cloud.util.listener

import dev.jonaz.cloud.components.setup.InstallationSetup
import dev.jonaz.cloud.components.setup.NetworkSetup
import dev.jonaz.cloud.components.setup.StructureSetup
import dev.jonaz.cloud.components.setup.SystemSetup
import dev.jonaz.cloud.util.exposed.DatabaseInitializer
import dev.jonaz.cloud.util.exposed.SchemaManager
import dev.jonaz.cloud.util.socket.SocketMappingInitializer
import dev.jonaz.cloud.util.socket.SocketServer
import dev.jonaz.cloud.util.system.ErrorLogging
import dev.jonaz.cloud.util.system.SystemPathManager
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class ApplicationStartupListener : InitializingBean {
    @Autowired
    lateinit var env: Environment

    override fun afterPropertiesSet() {
        SystemSetup().isCompatible()
        SystemPathManager().setSystemPath(env)
        ErrorLogging().createFile()

        StructureSetup().createDirectories()

        NetworkSetup().setupNetwork()

        SocketServer().startAsync()
        SocketMappingInitializer().setMapping()

        DatabaseInitializer().connect()
        SchemaManager().createSchema()

        InstallationSetup().startInstallation()
    }
}
