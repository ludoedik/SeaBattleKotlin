package org.eduard

import java.net.ServerSocket
import kotlin.concurrent.thread
import kotlin.system.exitProcess

fun main() {
    Server()
}
class Server {
    private var PORT : Int
    private val socket: ServerSocket
    init {
        try {
            val configReader = PropertiesReader.instance
            PORT = configReader.properties.get("port")?.toInt() ?: throw Exception("Port is not configured")
            println("Server started on port $PORT")
        }
        catch (ex: Exception) {
            println("Error while reading configurations. Server did not start.\nCause: ${ex.localizedMessage}")
            exitProcess(0)
        }
        socket = ServerSocket(PORT)
    }

    fun run() {
        while (true) {
            val client = socket.accept()
            println("Client connected: ${client.inetAddress.hostAddress}")
            // Run client in it's own thread.
            thread { ClientHandlerSeaBattle(client).handle() }
        }
    }

}