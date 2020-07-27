package dev.jonaz.cloud.util.docker.container

import com.google.gson.Gson
import dev.jonaz.cloud.model.docker.DockerStatsModel
import dev.jonaz.cloud.util.socket.SocketServer
import dev.jonaz.cloud.util.system.ErrorLogging
import dev.jonaz.cloud.util.system.SystemRuntime
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DockerStats {

    fun startStreamToChannel(name: String) = GlobalScope.launch {
        val process = SystemRuntime().execRaw("docker", "stats", name, "--format='{{json .}}'")
        val reader = process.inputStream.bufferedReader()
        val error = process.errorStream.bufferedReader()

        reader.forEachLine { s ->
            val json = s.slice(8 until s.length - 1)
            val stats = Gson().fromJson(json, DockerStatsModel::class.java)

            SocketServer.server.getRoomOperations(name).sendEvent("updateStats", json)

            if (stats.pids == "0") {
                process.destroyForcibly()
                return@forEachLine
            }
        }

        error.forEachLine {
            ErrorLogging().append(it)
            process.destroyForcibly()
            return@forEachLine
        }
    }

    fun getStats(name: String): DockerStatsModel? {
        val raw = SystemRuntime().exec("docker", "stats", name, "--no-stream", "--format='{{json .}}'").toString()

        val json = raw.slice(2 until raw.length - 6)

        return Gson().fromJson(json, DockerStatsModel::class.java)
    }
}
