package dev.jonaz.cloud.controller.socket.manage.static

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.components.manage.StaticManager
import dev.jonaz.cloud.util.docker.container.DockerLogs
import dev.jonaz.cloud.util.docker.network.DockerPort
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketController
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

@SocketMapping("/manage/static/get", SocketGuard.ADMIN)
class ManageStaticGetController : SocketController {

    override fun onEvent(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<Model>(dataMap)
        val name = "cloud-static-${data.name}"
        val result = StaticManager().getStatic(data.name)

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

    data class Model(
        val name: String
    )
}
