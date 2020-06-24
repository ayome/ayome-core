package dev.jonaz.cloud.util.docker.network

import dev.jonaz.cloud.util.system.SystemRuntime

class DockerPort {

    fun getByName(name: String): List<String> {
        val result = SystemRuntime().exec("docker port $name")
        return result.first
    }
}
