@file:Suppress("unused")

package dev.jonaz.cloud.web.controller

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.components.manage.ProxyManager
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

class SocketManageProxyController {

    @SocketMapping("/manage/proxy/get", SocketGuard.ADMIN)
    fun manageProxyGet(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<ManageProxyGetData>(dataMap)
        val result = ProxyManager().getProxy(data.name)

        ackRequest.sendAckData(mapOf("success" to result.first, "data" to result.second))
    }

    @SocketMapping("/manage/proxy/install", SocketGuard.ADMIN)
    fun manageProxyInstall(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<ManageProxyInstallData>(dataMap)
        val result = ProxyManager().installProxy(data.name, 512, 25565)

        ackRequest.sendAckData(mapOf("success" to result))
    }

    data class ManageProxyGetData(
        val name: String
    )

    data class ManageProxyInstallData(
        val name: String
    )
}
