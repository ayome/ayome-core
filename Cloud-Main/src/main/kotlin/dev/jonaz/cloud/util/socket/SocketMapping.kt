package dev.jonaz.cloud.util.socket

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class SocketMapping(val path: String, val permission: SocketGuard)
