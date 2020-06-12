package dev.jonaz.cloud.util.socket

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

object SocketData {
    inline fun <reified T>map(data: Map<*, *>): T {
        val mapper = jacksonObjectMapper()
        val objdata = ObjectMapper().writeValueAsString(data)
        return mapper.readValue(objdata.toString())
    }
}
