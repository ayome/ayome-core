package dev.jonaz.cloud.controller.socket.auth

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.components.auth.AuthLogin
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.socket.SocketController
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

@SocketMapping("/auth/login", SocketGuard.ALLOW)
class AuthLoginController : SocketController {

    override fun onEvent(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val data = SocketData.map<Model>(dataMap)

        val result = AuthLogin(client).validateCredentialsAndAddSession(data.username, data.password)

        ackRequest.sendAckData(mapOf("success" to result.first, "data" to result.second))
    }

    private data class Model(
        val username: String? = null,
        val password: String? = null
    )
}
