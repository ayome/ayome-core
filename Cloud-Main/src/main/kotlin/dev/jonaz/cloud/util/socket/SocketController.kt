package dev.jonaz.cloud.util.socket

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.util.session.SessionData

interface SocketController {
    fun onEvent(
        client: SocketIOClient,
        dataMap: Map<*, *>,
        ackRequest: AckRequest,
        session: SessionData?
    )
}
