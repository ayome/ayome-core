package dev.jonaz.cloud.controller.socket.manage.proxy

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.components.manage.ProxyManager
import dev.jonaz.cloud.util.docker.container.DockerLogs
import dev.jonaz.cloud.util.docker.container.DockerStats
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketController
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

@SocketMapping("/manage/proxy/install", SocketGuard.ADMIN)
class ManageProxyInstallController : SocketController {

    override fun onEvent(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<Model>(dataMap)

        val result = ProxyManager().installProxy(data.name, 512, 25565)

        if (result) DockerLogs().startLoggingToChannel(
            "cloud-proxy-${data.name}", // Container name
            "cloud-proxy-${data.name}", // Channel name
            "log-proxy-${data.name}" // Event name
        ).also {
            DockerStats().startStreamToChannel("cloud-proxy-${data.name}")
        }

        ackRequest.sendAckData(
            mapOf("success" to result)
        )
    }

    private data class Model(
        val name: String
    )
}
