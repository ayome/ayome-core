package dev.jonaz.cloud.util.docker.container

import dev.jonaz.cloud.util.socket.SocketServer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader

class DockerLogs {

    fun getLogs(containerName: String, limit: Int): List<String> {
        val process = ProcessBuilder("docker", "logs", "--tail", limit.toString(), containerName)
            .redirectErrorStream(true)
            .start()
        val result = mutableListOf<String>()

        fun addToResult(reader: BufferedReader) = reader.forEachLine line@{ s ->
            when {
                s.isEmpty() -> return@line
                s.equals(">") -> return@line
                else -> result.add(s)
            }
        }

        addToResult(process.inputStream.bufferedReader())

        return result
    }

    fun startLoggingToChannel(containerName: String, channelName: String, eventName: String) {
        val process = ProcessBuilder("docker", "logs", "-f", "--tail", "100", containerName)
            .redirectErrorStream(true)
            .start()
        val reader = process.inputStream.bufferedReader()

        GlobalScope.launch {
            reader.forEachLine line@{ s ->
                when {
                    s.isEmpty() -> return@line
                    s.equals(">") -> return@line
                    else -> SocketServer.server.getRoomOperations(channelName).sendEvent(eventName, s)
                }
            }
        }
    }
}
