package dev.jonaz.cloud.model.config.proxy

data class ProxyConfigServerModel(
    val motd: String,
    val address: String,
    val restricted: Boolean
)
