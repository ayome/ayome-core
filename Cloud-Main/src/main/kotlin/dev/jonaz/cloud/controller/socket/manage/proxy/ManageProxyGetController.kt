package dev.jonaz.cloud.controller.socket.manage.proxy

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.components.manage.ProxyManager
import dev.jonaz.cloud.util.docker.container.DockerLogs
import dev.jonaz.cloud.util.docker.port.DockerPort
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketController
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

@SocketMapping("/manage/proxy/get", SocketGuard.ADMIN)
class ManageProxyGetController : SocketController {

    override fun onEvent(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<Model>(dataMap)
        val name = "cloud-proxy-${data.name}"
        val result = ProxyManager().getProxy(data.name)

        val logs = DockerLogs().getLogs(name, 100)
        val ports = DockerPort().getByName(name)

        client.joinRoom(name)

        ackRequest.sendAckData(
            mapOf(
                "success" to result.first,
                "data" to result.second,
                "log" to logs,
                "ports" to ports
            )
        )
    }

    private data class Model(
        val name: String
    )
}
