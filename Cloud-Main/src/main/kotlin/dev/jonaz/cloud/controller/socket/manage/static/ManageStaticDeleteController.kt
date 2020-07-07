package dev.jonaz.cloud.controller.socket.manage.static

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.components.manage.StaticManager
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketController
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

@SocketMapping("/manage/static/remove", SocketGuard.ADMIN)
class ManageStaticDeleteController : SocketController {
    override fun onEvent(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<Model>(dataMap)

        val success = StaticManager().remove(data.name)

        ackRequest.sendAckData(
            mapOf("success" to success)
        )
    }

    data class Model(
        val name: String
    )
}
