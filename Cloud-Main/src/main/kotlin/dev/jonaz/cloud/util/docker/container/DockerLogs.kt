package dev.jonaz.cloud.util.docker.container

import dev.jonaz.cloud.util.socket.SocketServer
import dev.jonaz.cloud.util.system.SystemRuntime

class DockerLogs {

    fun getLogs(containerName: String, limit: Int): List<String> {
        val process = SystemRuntime().execRaw("docker logs $containerName")
        val bufferedReader = process.inputStream.bufferedReader()

        var row = 0
        val result = mutableListOf<String>()
        bufferedReader.forEachLine { s ->
            when {
                s.isEmpty() -> return@forEachLine
                s.equals(">") -> return@forEachLine
                limit < row -> return@forEachLine
                else -> {
                    row += 1
                    result.add(s)
                }
            }
        }
        return result
    }

    fun startLoggingToChannel(containerName: String, channelName: String, eventName: String) {
        val inputStream = SystemRuntime().execRaw("docker logs -f $containerName").inputStream
        val bufferedReader = inputStream.bufferedReader()

        bufferedReader.forEachLine line@{ s ->
            when {
                s.isEmpty() -> return@line
                s.equals(">") -> return@line
                else -> {
                    SocketServer.server.getRoomOperations(channelName).sendEvent(eventName, s)
                }
            }
        }
    }
}
