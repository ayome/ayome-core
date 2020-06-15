package dev.jonaz.cloud.controller.socket.auth

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.session.SessionManager
import dev.jonaz.cloud.util.socket.SocketController
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

@SocketMapping("/auth/validate", SocketGuard.ALLOW)
class AuthValidateController : SocketController {

    override fun onEvent(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val valid = SessionManager().validate(session?.token, client)

        ackRequest.sendAckData(mapOf("success" to valid))
    }
}
