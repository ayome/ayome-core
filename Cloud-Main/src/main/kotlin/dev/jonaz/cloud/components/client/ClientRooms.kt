package dev.jonaz.cloud.components.client

import com.corundumstudio.socketio.SocketIOClient

class ClientRooms {

    fun leaveAllDynamicRooms(client: SocketIOClient) {
        val rooms = client.allRooms.filter {
            it.contains("cloud-proxy") or
                    it.contains("cloud-static") or
                    it.contains("cloud-dynamic")
        }

        rooms.forEach { roomName ->
            client.leaveRoom(roomName)
        }
    }
}
