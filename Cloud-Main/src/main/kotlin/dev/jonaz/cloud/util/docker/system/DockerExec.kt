package dev.jonaz.cloud.util.docker.system

import com.pty4j.PtyProcess
import java.io.PrintWriter

class DockerExec {

    fun pty(container: String, command: String) {
        val cmd = arrayOf("docker", "exec", "-it", container, "/bin/sh")

        val pty = PtyProcess.exec(cmd)
        val outputStream = pty.outputStream

        val pw = PrintWriter(outputStream)
        pw.println(command)
        pw.close()

        pty.waitFor()
        pty.destroyForcibly()
    }
}
