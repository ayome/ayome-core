package dev.jonaz.cloud.util.system

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.URL

class NetworkUtils {

    fun getPublicAddress(): InetAddress? {
        val request = URL("http://checkip.amazonaws.com")
        val stream = request.openStream()
        val streamReader = InputStreamReader(stream)
        val bufferedReader = BufferedReader(streamReader)

        return InetAddress.getByName(bufferedReader.readLine())
    }
}
