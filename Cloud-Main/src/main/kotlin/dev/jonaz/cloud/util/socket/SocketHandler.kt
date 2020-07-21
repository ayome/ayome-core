package dev.jonaz.cloud.util.socket

import com.google.gson.Gson
import java.net.Socket
import java.nio.charset.Charset
import kotlin.concurrent.thread

class SocketHandler {
    companion object {
        val clients = mutableListOf<Socket>()
        val inactive = mutableListOf<Socket>()
    }

    fun acceptClients() = thread {
        val server = SocketServer.tcp

        while (true) {
            val client = server.accept()
            thread {
                clients.add(client)
                write(mapOf("event" to "welcome"), client)
            }
        }
    }

    fun broadcast(event: String, data: Map<*, *>) {
        val jsonData = Gson().toJson(data)
        val message = mapOf("event" to event, "data" to jsonData)

        clients.forEach { socket -> write(message, socket) }
        removeInactive()
    }

    private fun write(map: Map<*, *>, socket: Socket) {
        try {
            val outputStream = socket.getOutputStream()
            val json = Gson().toJson(map) + "\n"
            outputStream.write(
                json.toByteArray(Charset.defaultCharset())
            )
        } catch (e: Exception) {
            inactive.add(socket)
        }
    }

    private fun removeInactive() = inactive.forEach { socket ->
        socket.close()
        clients.remove(socket)
    }
}
