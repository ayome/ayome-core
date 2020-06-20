package dev.jonaz.cloud.util.docker.container

import dev.jonaz.cloud.util.socket.SocketServer
import dev.jonaz.cloud.util.system.SystemRuntime
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DockerLogs {

    fun getLogs(containerName: String, limit: Int): List<String> {
        val process = SystemRuntime().execRaw("docker logs --tail $limit $containerName")
        val bufferedReader = process.inputStream.bufferedReader()

        val result = mutableListOf<String>()
        bufferedReader.forEachLine { s ->
            when {
                s.isEmpty() -> return@forEachLine
                s.equals(">") -> return@forEachLine
                else -> result.add(s)
            }
        }
        return result
    }

    fun startLoggingToChannel(containerName: String, channelName: String, eventName: String) = GlobalScope.launch {
        val inputStream = SystemRuntime().execRaw("docker logs -f --tail 100 $containerName").inputStream
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
