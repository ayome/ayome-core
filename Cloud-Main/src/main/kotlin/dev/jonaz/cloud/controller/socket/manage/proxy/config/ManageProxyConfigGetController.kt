package dev.jonaz.cloud.controller.socket.manage.proxy.config

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import com.google.gson.Gson
import dev.jonaz.cloud.model.docker.DockerInspectHostConfigPortBindsModel
import dev.jonaz.cloud.util.docker.container.DockerInspect
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketController
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

@SocketMapping("/manage/proxy/config/get", SocketGuard.ADMIN)
class ManageProxyConfigGetController : SocketController {

    override fun onEvent(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<Model>(dataMap)
        val name = "cloud-proxy-${data.name}"
        val inspect = DockerInspect().getByName(name)

        val port = inspect.second?.hostConfig?.portBindings?.let { map ->
            val entry = map[map.keys.first()]
            val binds = entry?.let { SocketData.map<List<DockerInspectHostConfigPortBindsModel>>(it) }
            return@let binds?.get(0)?.hostPort ?: 0
        }

        println(port)

        ackRequest.sendAckData()
    }

    data class Model(
        val name: String
    )
}
