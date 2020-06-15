package dev.jonaz.cloud.util.socket

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.Application
import dev.jonaz.cloud.util.session.SessionData
import dev.jonaz.cloud.util.session.SessionManager
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import java.lang.reflect.Method
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.reflections.scanners.TypeAnnotationsScanner

class SocketMappingInitializer {

    fun setMapping() {
        val reflections = Reflections(Application::class.java)
        val annotated = reflections.getTypesAnnotatedWith(SocketMapping::class.java)

        annotated.forEach { t ->
            val permission = t.getAnnotation(SocketMapping::class.java).permission
            val path = t.getAnnotation(SocketMapping::class.java).path
            val instance = t.getDeclaredConstructor().newInstance()

            SocketServer.server.addEventListener(path, Map::class.java)
            { client, fullData, ackRequest -> launchMapping(t, instance, permission, client, fullData, ackRequest) }
        }
    }

    private fun launchMapping(
        annotatedClass: Class<*>,
        instance: Any,
        permission: SocketGuard,
        client: SocketIOClient,
        fullData: Map<*, *>?,
        ackRequest: AckRequest
    ) = GlobalScope.launch {
        val sessionToken = fullData?.get("session").toString()
        val session = SessionManager().get(sessionToken)
        val access = permission.validateAuthority(sessionToken, client)

        val parsedData: Map<*, *> = try {
            fullData?.get("data") as Map<*, *>
        } catch (e: Exception) {
            mapOf<Any, Any>()
        }

        if (!access) {
            return@launch
        }

        try {
            annotatedClass.getMethod(
                SocketController::onEvent.name,
                SocketIOClient::class.java,
                Map::class.java,
                AckRequest::class.java,
                SessionData::class.java
            ).invoke(
                instance,
                client,
                parsedData,
                ackRequest,
                session
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return@launch
        }
    }
}
