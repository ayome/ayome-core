package dev.jonaz.cloud.controller.socket.manage.proxy.config

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import com.google.gson.Gson
import dev.jonaz.cloud.components.manage.ProxyManager
import dev.jonaz.cloud.model.docker.DockerInspectHostConfigPortBindsModel
import dev.jonaz.cloud.util.docker.container.DockerInspect
import dev.jonaz.cloud.util.docker.container.DockerUpdate
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketController
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

@SocketMapping("/manage/proxy/config/save", SocketGuard.ADMIN)
class ManageProxyConfigSaveController : SocketController {

    override fun onEvent(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<Model>(dataMap)
        val name = "cloud-proxy-${data.name}"

        DockerUpdate().updateMemory(name, data.memory, data.memory)

        ackRequest.sendAckData(
            mapOf(
                "success" to true
            )
        )
    }

    data class Model(
        val name: String,
        val memory: Long
    )
}
