@file:Suppress("unused")

package dev.jonaz.cloud.web.controller

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.components.manage.ProxyManager
import dev.jonaz.cloud.util.docker.container.DockerContainer
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

class SocketManageProxyController {

    @SocketMapping("/manage/proxy/get", SocketGuard.ADMIN)
    fun manageProxyGet(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<ManageProxyData>(dataMap)
        val result = ProxyManager().getProxy(data.name)
        ackRequest.sendAckData(mapOf("success" to result.first, "data" to result.second))
    }

    @SocketMapping("/manage/proxy/install", SocketGuard.ADMIN)
    fun manageProxyInstall(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<ManageProxyInstallData>(dataMap)
        val result = ProxyManager().installProxy(data.name, 512, 25565)
        ackRequest.sendAckData(mapOf("success" to result))
    }

    @SocketMapping("/manage/proxy/stop", SocketGuard.ADMIN)
    fun manageProxyStop(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<ManageProxyData>(dataMap)
        val success = DockerContainer().stop(data.name)
        ackRequest.sendAckData(mapOf("success" to success))
    }

    @SocketMapping("/manage/proxy/start", SocketGuard.ADMIN)
    fun manageProxyStart(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<ManageProxyData>(dataMap)
        val success = DockerContainer().start(data.name)
        ackRequest.sendAckData(mapOf("success" to success))
    }

    data class ManageProxyData(
        val name: String
    )

    data class ManageProxyInstallData(
        val name: String
    )
}
