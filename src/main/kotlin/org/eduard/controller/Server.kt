package org.eduard.controller

import org.eduard.PropertiesReader
import org.eduard.exception.InitializingException
import org.eduard.handler.ClientHandlerSeaBattle
import java.net.ServerSocket
import kotlin.concurrent.thread

class Server {
    private var PORT: Int
    private val socket: ServerSocket

    init {
        try {
            val configReader = PropertiesReader.instance
            PORT = configReader.properties.get("port")?.toInt() ?: throw Exception("Port configuration is missing")
        } catch (ex: Exception) {
            println("Error while reading configurations.\nCause: ${ex.localizedMessage}")
            throw InitializingException()
        }
        socket = ServerSocket(PORT)
        println("Server started on port $PORT")
    }

    fun run() {
        while (true) {
            val client = socket.accept()
            println("Client connected: ${client.inetAddress.hostAddress}")
            thread { ClientHandlerSeaBattle(client).handle() }
        }
    }

}