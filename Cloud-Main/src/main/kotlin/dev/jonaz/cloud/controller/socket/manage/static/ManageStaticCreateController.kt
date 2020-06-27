package dev.jonaz.cloud.controller.socket.manage.static

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.components.manage.StaticManager
import dev.jonaz.cloud.components.manage.StaticValidator
import dev.jonaz.cloud.util.docker.container.DockerLogs
import dev.jonaz.cloud.util.docker.container.DockerStats
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketController
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping
import org.springframework.util.SocketUtils

@SocketMapping("/manage/static/create", SocketGuard.ADMIN)
class ManageStaticCreateController : SocketController {

    override fun onEvent(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<Model>(dataMap)

        val nameValidation = StaticValidator().validateName(data.name)
        if (nameValidation.first.not()) {
            ackRequest.sendAckData(Response(false, nameValidation.second))
            return
        }

        if (data.memory == 0L) {
            ackRequest.sendAckData(Response(false, "Invalid memory value"))
            return
        }

        val name = "cloud-static-${data.name}"
        val port = SocketUtils.findAvailableTcpPort()

        val result = StaticManager().installStatic(data.name, data.memory, port, data.version)

        if (result) {
            DockerStats().startStreamToChannel(name)
            DockerLogs().startLoggingToChannel(name, name, "updateLog")
        }

        ackRequest.sendAckData(Response(result, null))
    }

    data class Response(
        val success: Boolean,
        val message: String?
    )

    data class Model(
        val name: String,
        val memory: Long,
        val version: String
    )
}
