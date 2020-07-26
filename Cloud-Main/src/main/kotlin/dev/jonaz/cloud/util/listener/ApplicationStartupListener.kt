package dev.jonaz.cloud.util.listener

import dev.jonaz.cloud.components.setup.*
import dev.jonaz.cloud.util.exposed.DatabaseInitializer
import dev.jonaz.cloud.util.exposed.SchemaManager
import dev.jonaz.cloud.util.socket.SocketMappingInitializer
import dev.jonaz.cloud.util.socket.SocketServer
import dev.jonaz.cloud.util.system.ApplicationConfigManager
import dev.jonaz.cloud.util.system.EnvironmentUtils
import dev.jonaz.cloud.util.system.ErrorLogging
import dev.jonaz.cloud.util.system.SystemPathManager
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import javax.annotation.PreDestroy

@Component
class ApplicationStartupListener : InitializingBean {
    @Autowired
    lateinit var env: Environment

    override fun afterPropertiesSet() {
        SystemSetup().isCompatible()
        EnvironmentUtils().setEnvironment(env)
        SystemPathManager().setSystemPath()
        ApplicationConfigManager().setup()
        ErrorLogging().createFile()

        StructureSetup().createDirectories()
        InternalSetup().setupDefaultFiles()

        NetworkSetup().setupNetwork()

        SocketServer().startAsync()
        SocketMappingInitializer().setMapping()

        DatabaseInitializer().connect()
        SchemaManager().createSchema()

        InstallationSetup().startInstallation()
    }
}
