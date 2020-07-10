package dev.jonaz.cloud.model.config.proxy

data class ProxyConfigModel(
    var server_connect_timeout: Int,
    var listeners: List<ProxyConfigListenerModel>,
    var remote_ping_cache: Int,
    var network_compression_threshold: Int,
    var permissions: Any,
    var long_pings: Boolean,
    var connection_throttle_limit: Int,
    var prevent_proxy_connections: Boolean,
    var timeout: Int,
    var player_limit: Int,
    var ip_forward: Boolean,
    var groups: Any,
    var remote_ping_timeout: Int,
    var connection_throttle: Int,
    var log_commands: Boolean,
    var stats: String,
    var online_mode: Boolean,
    var forge_support: Boolean,
    var disabled_commands: Any,
    var servers: Map<String, ProxyConfigServerModel>
)
