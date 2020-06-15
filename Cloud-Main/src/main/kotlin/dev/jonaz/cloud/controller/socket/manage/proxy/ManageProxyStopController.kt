package dev.jonaz.cloud.controller.socket.manage.proxy

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.util.docker.container.DockerContainer
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketController
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

@SocketMapping("/manage/proxy/stop", SocketGuard.ADMIN)
class ManageProxyStopController : SocketController {

    override fun onEvent(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<Model>(dataMap)

        val success = DockerContainer().stop("cloud-proxy-${data.name}")

        ackRequest.sendAckData(
            mapOf("success" to success)
        )
    }

    private data class Model(
        val name: String
    )
}
