package dev.jonaz.cloud.controller.socket.manage.static

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.components.manage.StaticManager
import dev.jonaz.cloud.model.server.StaticServerModel
import dev.jonaz.cloud.util.docker.container.DockerInspect
import dev.jonaz.cloud.util.docker.container.DockerStats
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketController
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

@SocketMapping("/manage/static/list", SocketGuard.ADMIN)
class ManageStaticListController : SocketController {

    override fun onEvent(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val servers = StaticManager().getAll()
        val list = mutableListOf<StaticServerModel>()

        servers.forEach { s ->
            val name = "cloud-static-${s.name}"
            val server = DockerInspect().getByName(name)

            if (server.first) {
                val stats = DockerStats().getStats(name)
                val details = mapOf(
                    "status" to server.second?.state?.status,
                    "cpuPerc" to stats?.cpuPerc,
                    "memUsage" to stats?.memUsage,
                    "memPerc" to stats?.memPerc
                )
                val model = StaticServerModel(s.name, details)

                list.add(model)
            }
        }

        ackRequest.sendAckData(
            mapOf(
                "servers" to list
            )
        )
    }
}
