package dev.jonaz.cloud.util.socket

import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.util.session.SessionManager

enum class SocketGuard {
    ALLOW {
        override fun validateAuthority(sessionToken: String, client: SocketIOClient): Boolean = true
    },

    ADMIN {
        override fun validateAuthority(sessionToken: String, client: SocketIOClient): Boolean = SessionManager().validate(sessionToken, client)
    };

    abstract fun validateAuthority(sessionToken: String, client: SocketIOClient): Boolean
}
