package dev.jonaz.cloud.util.docker.system

import com.pty4j.PtyProcess
import java.io.PrintWriter
import java.util.concurrent.TimeUnit

class DockerExec {

    fun pty(container: String, command: String, timeout: Int = 0) {
        val cmd = arrayOf("docker", "exec", "-it", container, "/bin/sh")

        val pty = PtyProcess.exec(cmd)
        val outputStream = pty.outputStream

        val pw = PrintWriter(outputStream)
        pw.println(command)
        pw.close()

        if (timeout != 0) pty.waitFor(timeout.toLong(), TimeUnit.MILLISECONDS)
        else pty.waitFor()

        pty.destroyForcibly()
    }
}
