package dev.jonaz.cloud.controller.socket.manage.proxy.config

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.components.manage.ProxyManager
import dev.jonaz.cloud.components.manage.ServerManager
import dev.jonaz.cloud.components.manage.config.ProxyConfigManager
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

        val config = ProxyConfigManager(data.name).get()
        val inspect = ProxyManager().getProxy(data.name)
        val allServers = ServerManager().getAll()

        ackRequest.sendAckData(
            mapOf(
                "inspect" to inspect.second,
                "allServers" to allServers,
                "config" to config
            )
        )
    }

    data class Model(
        val name: String
    )
}
