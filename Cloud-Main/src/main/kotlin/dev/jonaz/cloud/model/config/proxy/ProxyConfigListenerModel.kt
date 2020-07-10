package dev.jonaz.cloud.model.config.proxy

data class ProxyConfigListenerModel(
    var query_port: Int,
    var motd: String,
    var tab_list: String,
    var query_enabled: Boolean,
    var proxy_protocol: Boolean,
    var forced_hosts: Any,
    var ping_passthrough: Boolean,
    var priorities: MutableList<String>,
    var bind_local_address: Boolean,
    var host: String,
    var max_players: Int,
    var tab_size: Int,
    var force_default_server: Boolean
)
