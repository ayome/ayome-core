@file:Suppress("unused")

package dev.jonaz.cloud.web.controller

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.components.auth.AuthLogin
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.session.SessionManager
import dev.jonaz.cloud.util.socket.SocketData
import dev.jonaz.cloud.util.socket.SocketGuard
import dev.jonaz.cloud.util.socket.SocketMapping

class SocketAuthController {

    @SocketMapping("/auth/login", SocketGuard.ALLOW)
    fun authLogin(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val login = AuthLogin(client)
        val data = SocketData.map<AuthLoginData>(dataMap)
        val result = login.validateCredentialsAndAddSession(data.username, data.password)

        ackRequest.sendAckData(mapOf("success" to result.first, "data" to result.second))
    }

    @SocketMapping("/auth/validate", SocketGuard.ALLOW)
    fun authValidate(client: SocketIOClient, dataMap: Map<*, *>, ackRequest: AckRequest, session: SessionData?) {
        val valid = SessionManager().validate(session?.token, client)
        ackRequest.sendAckData(mapOf("success" to valid))
    }

    data class AuthLoginData(
        val username: String? = null,
        val password: String? = null
    )
}
