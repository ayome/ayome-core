package dev.jonaz.cloud.controller.socket.manage.proxy

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.util.docker.container.DockerContainer
import dev.jonaz.cloud.util.docker.container.DockerLogs
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketController
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

@SocketMapping("/manage/proxy/start", SocketGuard.ADMIN)
class ManageProxyStartController : SocketController {

    override fun onEvent(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<Model>(dataMap)

        val success = DockerContainer().start("cloud-proxy-${data.name}")

        DockerLogs().startLoggingToChannel(
            "cloud-proxy-${data.name}", // Container name
            "cloud-proxy-${data.name}", // Channel name
            "log-proxy-${data.name}" // Event name
        )

        ackRequest.sendAckData(
            mapOf("success" to success)
        )
    }

    data class Model(
        val name: String
    )
}
