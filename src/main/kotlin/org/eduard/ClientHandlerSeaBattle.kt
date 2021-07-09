package org.eduard

import java.io.PrintWriter
import java.lang.Exception
import java.net.Socket
import java.util.*

class ClientHandlerSeaBattle(client: Socket) : ClientHandler {
    private val client = client
    private val reader: Scanner = Scanner(client.getInputStream())
    private val writer = PrintWriter(client.getOutputStream())
    private var running: Boolean = false

    override fun handle() {
        running = true
        while (running) {
            try {
                val text = reader.nextLine()
                println(text)
                writer.println("Hehe")
                writer.flush()
            } catch (ex: Exception) {
                println("error. Message: ${ex.localizedMessage}")
                reader.close()
                writer.close()
                client.close()
                running = false;
            } finally {
                //client.close()
            }
        }
    }
}