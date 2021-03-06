package dev.jonaz.cloud.controller.socket.manage.static

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.components.manage.setup.StaticSetup
import dev.jonaz.cloud.util.docker.container.DockerContainer
import dev.jonaz.cloud.util.docker.container.DockerLogs
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketController
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

@SocketMapping("/manage/static/start", SocketGuard.ADMIN)
class ManageStaticStartController: SocketController {
    override fun onEvent(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<Model>(dataMap)
        val name = "cloud-static-${data.name}"

        StaticSetup(data.name).usePlugin("Cloud-Static.jar")

        val success = DockerContainer().start(name)

        DockerLogs().startLoggingToChannel(name, name, "updateLog")

        ackRequest.sendAckData(
            mapOf("success" to success)
        )
    }

    data class Model(
        val name: String
    )
}
