package dev.jonaz.cloud.util.session

import java.net.InetAddress

data class SessionData(
    val user: String? = null,
    val token: String? = null,
    val remoteAddress: InetAddress? = null
)
