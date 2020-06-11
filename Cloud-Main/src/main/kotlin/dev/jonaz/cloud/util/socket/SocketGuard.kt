package dev.jonaz.cloud.util.socket

import com.corundumstudio.socketio.SocketIOClient

enum class SocketGuard {
    ALLOW {
        override fun validateAuthority(sessionToken: String, client: SocketIOClient): Boolean = true
    },

    ADMIN {
        override fun validateAuthority(sessionToken: String, client: SocketIOClient): Boolean = false // TODO
    };

    abstract fun validateAuthority(sessionToken: String, client: SocketIOClient): Boolean
}
