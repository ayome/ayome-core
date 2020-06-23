package dev.jonaz.cloud.util.docker.port

import dev.jonaz.cloud.util.system.SystemRuntime

class DockerPort {

    fun getByName(name: String): List<String> {
        val result = SystemRuntime().exec("docker port $name")
        return result.first
    }
}
