package dev.jonaz.cloud.util.socket

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SocketData {
    inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, object : TypeToken<T>() {}.type)

    inline fun <reified T> map(data: Map<*, *>): T {
        val jsonData = Gson().toJson(data)
        return Gson().fromJson(jsonData)
    }
}
