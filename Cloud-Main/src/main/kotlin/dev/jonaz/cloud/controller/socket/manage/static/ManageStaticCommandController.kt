package dev.jonaz.cloud.controller.socket.manage.static

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.components.manage.StaticManager
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketController
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

@SocketMapping("/manage/static/command", SocketGuard.ADMIN)
class ManageStaticCommandController: SocketController {
    override fun onEvent(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<Model>(dataMap)
        val name = "cloud-static-${data.name}"

        StaticManager().exec(name, data.command)

        ackRequest.sendAckData()
    }

    data class Model(
        val name: String,
        val command: String
    )
}
