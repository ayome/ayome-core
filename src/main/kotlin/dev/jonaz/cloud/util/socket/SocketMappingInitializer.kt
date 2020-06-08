package dev.jonaz.cloud.util.socket

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.Application
import dev.jonaz.cloud.util.session.SessionManager
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import java.lang.reflect.Method
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SocketMappingInitializer {
    private val server = SocketServer.server

    init {
        val reflections = Reflections(Application::class.java, MethodAnnotationsScanner())
        val annotated = reflections.getMethodsAnnotatedWith(SocketMapping::class.java)

        for (method in annotated) {
            val instance = method.declaringClass.getDeclaredConstructor().newInstance()
            val path = method.getAnnotation(SocketMapping::class.java).path

            server.addEventListener(path, Map::class.java)
            { client, fullData, ackRequest -> launchMapping(method, instance, client, fullData, ackRequest) }
        }
    }

    private fun launchMapping(
        method: Method,
        instance: Any,
        client: SocketIOClient,
        fullData: Map<*, *>?,
        ackSender: AckRequest
    ) = GlobalScope.launch {

        val sessionToken = fullData?.getOrDefault("session", null).toString()
        val session = SessionManager().get(sessionToken)
        val parsedData = fullData?.getOrDefault("data", emptyMap<Any, Any>())

        val access = method.getAnnotation(SocketMapping::class.java).permission.validateAuthority(sessionToken, client)

        if (access) {
            method.invoke(instance, client, parsedData, ackSender, session)
        } else {
            ackSender.sendAckData(null)
        }
    }
}
