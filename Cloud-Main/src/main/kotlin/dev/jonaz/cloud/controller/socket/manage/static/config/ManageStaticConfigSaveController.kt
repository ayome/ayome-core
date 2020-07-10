package dev.jonaz.cloud.controller.socket.manage.static.config

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.util.docker.container.DockerUpdate
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketController
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

@SocketMapping("/manage/static/config/save", SocketGuard.ADMIN)
class ManageStaticConfigSaveController : SocketController {

    override fun onEvent(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<Model>(dataMap)
        val name = "cloud-static-${data.name}"

        DockerUpdate().updateMemory(name, data.memory, data.memory)

        ackRequest.sendAckData()
    }

    data class Model(
        val name: String,
        val memory: Long
    )
}
